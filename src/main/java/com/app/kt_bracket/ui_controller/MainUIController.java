package com.app.kt_bracket.ui_controller;

import com.app.kt_bracket.data.Category;
import com.app.kt_bracket.drawing.BracketDrawer;
import com.app.kt_bracket.logic.BracketBuilder;
import com.app.kt_bracket.logic.CategoryBuilder;
import com.app.kt_bracket.structure.Bracket;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@Component
public class MainUIController
{
    @Autowired
    CategoryBuilder categoryBuilder;

    @Autowired
    BracketBuilder bracketBuilder;

    @Autowired
    BracketDrawer bracketDrawer;

    @FXML
    GridPane bracketGridPane;

    @FXML
    public Button importCategoryButton;


    @FXML
    public void initialize()
    {

    }

    public void importCategoryItemAction(ActionEvent actionEvent)
    {
        FileChooser fileChooser = new FileChooser();

        if ( System.getProperty("os.name").contains("Linux") )
            fileChooser.setInitialDirectory(new File("/home/ubuntu/pliki_robocze/"));
        if ( System.getProperty("os.name").contains("Windows") )
            fileChooser.setInitialDirectory(new File("C:\\Users"));

        File fileWithCategory = fileChooser.showOpenDialog(null);
        List<String> competitorStringsList = new ArrayList<>();

        if ( fileWithCategory == null ) return;

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

        Category category = categoryBuilder.build(competitorStringsList, fileWithCategory.getName());


        Bracket bracket = bracketBuilder.build(category);
        bracketDrawer.draw(bracket, bracketGridPane);
    }

    public void importCategoriesItemAction(ActionEvent actionEvent)
    {
        

    }
}
