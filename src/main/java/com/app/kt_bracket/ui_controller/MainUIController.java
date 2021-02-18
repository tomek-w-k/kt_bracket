package com.app.kt_bracket.ui_controller;

import com.app.kt_bracket.data.Category;
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
    @FXML
    MenuItem loadMenuItem;

    @Autowired
    CategoryBuilder categoryBuilder;

    @Autowired
    BracketBuilder bracketBuilder;

    @FXML
    GridPane bracketGridPane;


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
        Bracket bracket = bracketBuilder.build(category);
        bracket.getColumns().stream()
                .forEach(column -> {
                    System.out.println("Kolumna :: \n");
                    column.getFights().stream()
                            .forEach(fight -> {
                                System.out.println( fight.getShiro().getFullName() + " " +
                                                    fight.getShiro().getCoordinates().toString() );
                                System.out.println("\t\t\t" + fight.getWinner().getFullName() +
                                                    fight.getWinner().getCoordinates().toString());
                                System.out.println( fight.getAka().getFullName() + " " +
                                                    fight.getAka().getCoordinates().toString() );
                                System.out.println("Dekoracja :: " + fight.getDecoration() + "\n");
                            });
                });

        bracketGridPane.getChildren().clear();
        bracketGridPane.getColumnConstraints().clear();
        bracketGridPane.getRowConstraints().clear();

        IntStream.iterate(0, row -> ++row)
                .limit(bracket.getColumns().get(1).getFights().size() * 8)
                .forEach(row -> {
                    bracketGridPane.getRowConstraints().add(row, new RowConstraints(25));
                });

        IntStream.iterate(0, col -> ++col)
            .limit(bracket.getColumns().get( bracket.getColumns().size() - 1 ).getFights().get(0).getWinner().getCoordinates().getX())
            .forEach(col -> {
                ColumnConstraints c = new ColumnConstraints(200);

                c.setFillWidth(true);
                bracketGridPane.getColumnConstraints().add(col, c);
            });

        bracket.getColumns().get(0).getFights().stream()
            .forEach(fight -> {
                Label labelShiro = new Label(fight.getShiro().getFullName());
                labelShiro.setStyle("-fx-background-color: lightgray; -fx-border-color: black");
                AnchorPane shiroAnchorPane = new AnchorPane(labelShiro);
                AnchorPane.setTopAnchor(labelShiro, .00);
                AnchorPane.setBottomAnchor(labelShiro, .00);
                AnchorPane.setLeftAnchor(labelShiro, .00);
                AnchorPane.setRightAnchor(labelShiro, .00);
                bracketGridPane.add(shiroAnchorPane, fight.getShiro().getCoordinates().getX(), fight.getShiro().getCoordinates().getY());

                bracketGridPane.getColumnConstraints().get(fight.getNumberCoordinates().getX()).setPrefWidth(50);
                bracketGridPane.getColumnConstraints().get(fight.getNumberCoordinates().getX() + 1).setPrefWidth(50);
                Label labelNumber = new Label("##");
                labelNumber.setStyle("-fx-background-color: lightgray; -fx-border-color: black");
                AnchorPane numberAnchorPane = new AnchorPane(labelNumber);
                AnchorPane.setTopAnchor(labelNumber, .00);
                AnchorPane.setBottomAnchor(labelNumber, .00);
                AnchorPane.setLeftAnchor(labelNumber, .00);
                AnchorPane.setRightAnchor(labelNumber, .00);
                bracketGridPane.add(numberAnchorPane, fight.getNumberCoordinates().getX(), fight.getNumberCoordinates().getY());

                Label labelWinner = new Label(fight.getWinner().getFullName());
                labelWinner.setStyle("-fx-background-color: lightgray; -fx-border-color: black");
                AnchorPane winnerAnchorPane = new AnchorPane(labelWinner);
                AnchorPane.setTopAnchor(labelWinner, .00);
                AnchorPane.setBottomAnchor(labelWinner, .00);
                AnchorPane.setLeftAnchor(labelWinner, .00);
                AnchorPane.setRightAnchor(labelWinner, .00);
                bracketGridPane.add(winnerAnchorPane, fight.getWinner().getCoordinates().getX(), fight.getWinner().getCoordinates().getY());

                Label labelAka = new Label(fight.getAka().getFullName());
                labelAka.setStyle("-fx-background-color: lightgray; -fx-border-color: black");
                AnchorPane akaAnchorPane = new AnchorPane(labelAka);
                AnchorPane.setTopAnchor(labelAka, .00);
                AnchorPane.setBottomAnchor(labelAka, .00);
                AnchorPane.setLeftAnchor(labelAka, .00);
                AnchorPane.setRightAnchor(labelAka, .00);
                bracketGridPane.add(akaAnchorPane, fight.getAka().getCoordinates().getX(), fight.getAka().getCoordinates().getY());

                // shiro horizontal line
                Polyline shiroHLine =  new Polyline();
                GridPane.setHalignment(shiroHLine, HPos.CENTER);
                GridPane.setValignment(shiroHLine, VPos.CENTER);
                shiroHLine.getPoints().addAll(0.0, 0.0,
                        50.0, 0.0);
                bracketGridPane.add(shiroHLine, fight.getDecoration().getShiroHorizontalLine().getX(), fight.getDecoration().getShiroHorizontalLine().getY());

                // up - right corners
                Polyline upRightCornerLine = new Polyline();
                GridPane.setHalignment(upRightCornerLine, HPos.LEFT);
                GridPane.setValignment(upRightCornerLine, VPos.BOTTOM);
                upRightCornerLine.getPoints().addAll(0.0, 0.0 +1,
                        25.0 -1, 0.0 +1,
                        25.0 -1, 12.5);
                bracketGridPane.add(upRightCornerLine, fight.getDecoration().getUpRightCorner().getX(), fight.getDecoration().getUpRightCorner().getY());

                // t - junction
                Polyline tJunctionVPart = new Polyline();
                GridPane.setHalignment(tJunctionVPart, HPos.CENTER);
                GridPane.setValignment(tJunctionVPart, VPos.CENTER);
                tJunctionVPart.getPoints().addAll(50.0, -12.5,
                        50.0, 12.5);
                bracketGridPane.add(tJunctionVPart, fight.getDecoration().gettJunction().getX(), fight.getDecoration().gettJunction().getY());
                Polyline tJunctionHPart =  new Polyline();
                GridPane.setHalignment(tJunctionHPart, HPos.RIGHT);
                GridPane.setValignment(tJunctionHPart, VPos.CENTER);
                tJunctionHPart.setStrokeLineCap(StrokeLineCap.BUTT);
                tJunctionHPart.getPoints().addAll(0.0, 0.0,
                        25.0, 0.0);
                bracketGridPane.add(tJunctionHPart, fight.getDecoration().gettJunction().getX(), fight.getDecoration().gettJunction().getY());

                // down - right corners
                Polyline downRightCornerLine = new Polyline();
                GridPane.setHalignment(downRightCornerLine, HPos.LEFT);
                GridPane.setValignment(downRightCornerLine, VPos.TOP);
                downRightCornerLine.getPoints().addAll(0.0, 12.5 -0.5,
                        25.0 -1, 12.5 -0.5,
                        25.0 -1, 0.0);
                bracketGridPane.add(downRightCornerLine, fight.getDecoration().getDownRightCorner().getX(), fight.getDecoration().getDownRightCorner().getY());

                // aka horizontal line
                Polyline akaHLine =  new Polyline();
                GridPane.setHalignment(akaHLine, HPos.CENTER);
                GridPane.setValignment(akaHLine, VPos.CENTER);
                akaHLine.getPoints().addAll(0.0, 0.0,
                        50.0, 0.0);
                bracketGridPane.add(akaHLine, fight.getDecoration().getAkaHorizontalLine().getX(), fight.getDecoration().getAkaHorizontalLine().getY());


            });

        IntStream.iterate(1, col -> ++col)
            .limit(bracket.getColumns().size() - 1)
            .forEach(col -> {
                bracket.getColumns().get(col).getFights().stream()
                    .forEach(fight -> {
                        if ( col == 1 && !fight.getShiro().isEmpty() ) {
                            Label labelShiro = new Label(fight.getShiro().getFullName());
                            labelShiro.setStyle("-fx-background-color: lightgray; -fx-border-color: black");
                            AnchorPane shiroAnchorPane = new AnchorPane(labelShiro);
                            AnchorPane.setTopAnchor(labelShiro, .00);
                            AnchorPane.setBottomAnchor(labelShiro, .00);
                            AnchorPane.setLeftAnchor(labelShiro, .00);
                            AnchorPane.setRightAnchor(labelShiro, .00);
                            bracketGridPane.add(shiroAnchorPane, fight.getShiro().getCoordinates().getX(), fight.getShiro().getCoordinates().getY());
                        }

                        bracketGridPane.getColumnConstraints().get(fight.getNumberCoordinates().getX()).setPrefWidth(50);
                        bracketGridPane.getColumnConstraints().get(fight.getNumberCoordinates().getX() + 1).setPrefWidth(50);
                        Label labelNumber = new Label("##");
                        labelNumber.setStyle("-fx-background-color: lightgray; -fx-border-color: black");
                        AnchorPane numberAnchorPane = new AnchorPane(labelNumber);
                        AnchorPane.setTopAnchor(labelNumber, .00);
                        AnchorPane.setBottomAnchor(labelNumber, .00);
                        AnchorPane.setLeftAnchor(labelNumber, .00);
                        AnchorPane.setRightAnchor(labelNumber, .00);
                        bracketGridPane.add(numberAnchorPane, fight.getNumberCoordinates().getX(), fight.getNumberCoordinates().getY());

                        Label labelWinner = new Label(fight.getWinner().getFullName());
                        labelWinner.setStyle("-fx-background-color: lightgray; -fx-border-color: black");
                        AnchorPane winnerAnchorPane = new AnchorPane(labelWinner);
                        AnchorPane.setTopAnchor(labelWinner, .00);
                        AnchorPane.setBottomAnchor(labelWinner, .00);
                        AnchorPane.setLeftAnchor(labelWinner, .00);
                        AnchorPane.setRightAnchor(labelWinner, .00);
                        bracketGridPane.add(winnerAnchorPane, fight.getWinner().getCoordinates().getX(), fight.getWinner().getCoordinates().getY());

                        if ( col == 1 && !fight.getAka().isEmpty() ) {
                            Label labelAka = new Label(fight.getAka().getFullName());
                            labelAka.setStyle("-fx-background-color: lightgray; -fx-border-color: black");
                            AnchorPane akaAnchorPane = new AnchorPane(labelAka);
                            AnchorPane.setTopAnchor(labelAka, .00);
                            AnchorPane.setBottomAnchor(labelAka, .00);
                            AnchorPane.setLeftAnchor(labelAka, .00);
                            AnchorPane.setRightAnchor(labelAka, .00);
                            bracketGridPane.add(akaAnchorPane, fight.getAka().getCoordinates().getX(), fight.getAka().getCoordinates().getY());
                        }

                        // shiro horizontal line
                        Polyline shiroHLine =  new Polyline();
                        GridPane.setHalignment(shiroHLine, HPos.CENTER);
                        GridPane.setValignment(shiroHLine, VPos.CENTER);
                        shiroHLine.getPoints().addAll(0.0, 0.0,
                                50.0, 0.0);
                        bracketGridPane.add(shiroHLine, fight.getDecoration().getShiroHorizontalLine().getX(), fight.getDecoration().getShiroHorizontalLine().getY());

                        // up - right corners
                        Polyline upRightCornerLine = new Polyline();
                        GridPane.setHalignment(upRightCornerLine, HPos.LEFT);
                        GridPane.setValignment(upRightCornerLine, VPos.BOTTOM);
                        upRightCornerLine.getPoints().addAll(0.0, 0.0 +1,
                                                                    25.0 -1, 0.0 +1,
                                                                    25.0 -1, 12.5);
                        bracketGridPane.add(upRightCornerLine, fight.getDecoration().getUpRightCorner().getX(), fight.getDecoration().getUpRightCorner().getY());

                        fight.getDecoration().getWinnerTopVerticalLines().stream()
                            .forEach(winnerTopVLineCoordinates -> {
                                Polyline winnerTopVLine = new Polyline();
                                GridPane.setHalignment(winnerTopVLine, HPos.CENTER);
                                GridPane.setValignment(winnerTopVLine, VPos.CENTER);
                                winnerTopVLine.getPoints().addAll(50.0, -12.5,
                                        50.0, 12.5);
                                bracketGridPane.add(winnerTopVLine, winnerTopVLineCoordinates.getX(), winnerTopVLineCoordinates.getY());
                            });

                        // t - junction
                        Polyline tJunctionVPart = new Polyline();
                        GridPane.setHalignment(tJunctionVPart, HPos.CENTER);
                        GridPane.setValignment(tJunctionVPart, VPos.CENTER);
                        tJunctionVPart.getPoints().addAll(50.0, -12.5,
                                50.0, 12.5);
                        bracketGridPane.add(tJunctionVPart, fight.getDecoration().gettJunction().getX(), fight.getDecoration().gettJunction().getY());
                        Polyline tJunctionHPart =  new Polyline();
                        GridPane.setHalignment(tJunctionHPart, HPos.RIGHT);
                        GridPane.setValignment(tJunctionHPart, VPos.CENTER);
                        tJunctionHPart.setStrokeLineCap(StrokeLineCap.BUTT);
                        tJunctionHPart.getPoints().addAll(0.0, 0.0,
                                25.0, 0.0);
                        bracketGridPane.add(tJunctionHPart, fight.getDecoration().gettJunction().getX(), fight.getDecoration().gettJunction().getY());

                        fight.getDecoration().getWinnerBottomVerticalLines().stream()
                            .forEach(winnerBottomVLineCoordinates -> {
                                Polyline winnerBottomVLine = new Polyline();
                                GridPane.setHalignment(winnerBottomVLine, HPos.CENTER);
                                GridPane.setValignment(winnerBottomVLine, VPos.CENTER);
                                winnerBottomVLine.getPoints().addAll(50.0, -12.5,
                                        50.0, 12.5);
                                bracketGridPane.add(winnerBottomVLine, winnerBottomVLineCoordinates.getX(), winnerBottomVLineCoordinates.getY());
                            });

                        // down - right corners
                        Polyline downRightCornerLine = new Polyline();
                        GridPane.setHalignment(downRightCornerLine, HPos.LEFT);
                        GridPane.setValignment(downRightCornerLine, VPos.TOP);
                        downRightCornerLine.getPoints().addAll(0.0, 12.5 -0.5,
                                25.0 -1, 12.5 -0.5,
                                25.0 -1, 0.0);
                        bracketGridPane.add(downRightCornerLine, fight.getDecoration().getDownRightCorner().getX(), fight.getDecoration().getDownRightCorner().getY());

                        // aka horizontal line
                        Polyline akaHLine =  new Polyline();
                        GridPane.setHalignment(akaHLine, HPos.CENTER);
                        GridPane.setValignment(akaHLine, VPos.CENTER);
                        akaHLine.getPoints().addAll(0.0, 0.0,
                                50.0, 0.0);
                        bracketGridPane.add(akaHLine, fight.getDecoration().getAkaHorizontalLine().getX(), fight.getDecoration().getAkaHorizontalLine().getY());
                    });
            });

    }
}
