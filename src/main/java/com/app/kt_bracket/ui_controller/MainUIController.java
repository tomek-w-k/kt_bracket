package com.app.kt_bracket.ui_controller;

import com.app.kt_bracket.data.Category;
import com.app.kt_bracket.logic.BracketBuilder;
import com.app.kt_bracket.logic.CategoryBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class MainUIController
{
    @FXML
    MenuItem loadMenuItem;

    @Autowired
    CategoryBuilder categoryBuilder;

    @Autowired
    BracketBuilder bracketBuilder;


    @FXML
    public void initialize()
    {

    }

    public void loadMenuItemAction(ActionEvent actionEvent)
    {
        FileChooser fileChooser = new FileChooser();

        if ( System.getProperty("os.name").contains("Linux") )
            fileChooser.setInitialDirectory(new File("/home/ubuntu/pliki_robocze/"));
        if ( System.getProperty("os.name").contains("Windows") )
            fileChooser.setInitialDirectory(new File("C:\\Users"));

        File fileWithCategory = fileChooser.showOpenDialog(null);
        List<String> competitorStringsList = new ArrayList<>();

        try{
            FileReader fileReader = new FileReader(fileWithCategory);
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

                    competitorStringsList.add(person);
                }
            }
        }
        catch(FileNotFoundException e) { e.printStackTrace(); }
        catch(IOException e) { e.printStackTrace(); }

        competitorStringsList.forEach(System.out::println);
        System.out.println("Ilość zawodników :: " + competitorStringsList.size());

        Category category = categoryBuilder.build(competitorStringsList, fileWithCategory.getName());

        System.out.println("\nKolumny :: ");
        bracketBuilder.build(category).getColumns().stream()
                .forEach(column -> {
                    System.out.println("Kolumna :: \n");
                    column.getFights().stream()
                            .forEach(fight -> {
                                System.out.println(fight.getShiro().getCoordinates().toString());
                                System.out.println("\t\t\t" + fight.getWinner().getCoordinates().toString());
                                System.out.println(fight.getAka().getCoordinates().toString() + "\n");
                            });
                });


    }
}
