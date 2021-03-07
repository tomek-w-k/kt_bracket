package com.app.kt_bracket.importers;

import com.app.kt_bracket.data.Category;
import com.app.kt_bracket.logic.CategoryBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class TextFileImporter
{
    @Autowired
    CategoryBuilder categoryBuilder;


    public void importCategory(List<Category> categories)
    {
        FileChooser fileChooser = new FileChooser();

        if ( System.getProperty("os.name").contains("Linux") )
            fileChooser.setInitialDirectory(new File("/home/ubuntu/pliki_robocze/"));
        if ( System.getProperty("os.name").contains("Windows") )
            fileChooser.setInitialDirectory(new File("C:\\Users"));

        File file = fileChooser.showOpenDialog(null);
        List<String> persons = new ArrayList<>();

        if ( file == null ) return;
        if ( file.isDirectory() ) return;

        try {
            if ( !Files.probeContentType(Path.of(file.getAbsolutePath())).equals(MediaType.TEXT_PLAIN_VALUE) ) return;

            FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = null;
            while ( (line = bufferedReader.readLine()) != null )
            {
                String[] parts = line.trim().split("\\s+");
                if ( parts.length > 0 )
                {
                    String person = parts[0];
                    if ( parts.length > 1 )
                        person = person + " " + parts[1];

                    persons.add(person);
                }
            }
        }
        catch(FileNotFoundException e) { e.printStackTrace(); }
        catch(IOException e) { e.printStackTrace(); }

        categories.add( categoryBuilder.build(persons, new SimpleStringProperty(file.getName())) );
    }

    public void importCategories(List<Category> categories)
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        if ( System.getProperty("os.name").contains("Linux") )
            directoryChooser.setInitialDirectory(new File("/home/ubuntu/pliki_robocze/"));
        if ( System.getProperty("os.name").contains("Windows") )
            directoryChooser.setInitialDirectory(new File("C:\\Users"));

        File directoryWithCategories = directoryChooser.showDialog(null);

        if ( directoryWithCategories == null ) return;

        Arrays.asList(directoryWithCategories.listFiles()).stream()
            .forEach(file -> {
                if ( file.isDirectory() ) return;

                try {
                    if ( !Files.probeContentType(Path.of(file.getAbsolutePath())).equals(MediaType.TEXT_PLAIN_VALUE) ) return;

                    FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    List<String> persons = new ArrayList<>();

                    String line = null;
                    while ( (line = bufferedReader.readLine()) != null )
                    {
                        String[] parts = line.trim().split("\\s+");
                        if ( parts.length > 0 )
                        {
                            String person = parts[0];
                            if ( parts.length > 1 )
                                person = person + " " + parts[1];

                            if ( !person.isBlank() )
                                persons.add(person);
                        }
                    }

                    if ( persons.size() > 1 )
                        categories.add( categoryBuilder.build(persons, new SimpleStringProperty(file.getName())) );

                    fileReader.close();
                }
                catch(FileNotFoundException e) { e.printStackTrace(); }
                catch(IOException e) { e.printStackTrace(); }
            });
    }
}
