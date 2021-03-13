package com.app.kt_bracket.drawing;

import com.app.kt_bracket.structure.Bracket;
import com.app.kt_bracket.structure.Mat;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import org.springframework.stereotype.Component;
import java.util.stream.IntStream;


@Component
public class BracketDrawer
{
    private static final double COMPETITOR_COLUMN_WIDTH = 200;
    private static final double FIGHT_NUMBER_COLUMN_WIDTH = 50;
    private static final double DECORATION_COLUMN_WIDTH = 50;
    private static final double ROW_HEIGHT = 25;


    public void drawAll(Mat mat)
    {
        mat.getBrackets().stream()
            .forEach(bracket -> {
                GridPane bracketGridPane = new GridPane();
                this.draw(bracket, bracketGridPane);
                bracket.setLayoutRepresentation(bracketGridPane);
            });
    }

    public void draw(Bracket bracket, GridPane bracketGridPane)
    {
        bracketGridPane.getChildren().clear();
        bracketGridPane.getColumnConstraints().clear();
        bracketGridPane.getRowConstraints().clear();

        if ( bracket.getColumns().size() > 1 )
            IntStream.iterate(0, row -> ++row)
                .limit(bracket.getColumns().get(1).getFights().size() * 8)
                .forEach(row -> bracketGridPane.getRowConstraints().add(row, new RowConstraints(ROW_HEIGHT)));
        else
            IntStream.iterate(0, row -> ++row)
                .limit(3)
                .forEach(row -> bracketGridPane.getRowConstraints().add(row, new RowConstraints(ROW_HEIGHT)));

        // simpleColumn - a column of a bracketGridPane containing only a part of the fignt, i.e. only competitors, only fight numbers or decorations
        IntStream.iterate(0, simpleColumnNumber -> simpleColumnNumber + 3)
            .limit(bracket.getColumns().size())
            .forEach(simpleColumnNumber -> {
                ColumnConstraints forCompetitorColumn = new ColumnConstraints(COMPETITOR_COLUMN_WIDTH);
                forCompetitorColumn.setFillWidth(true);
                bracketGridPane.getColumnConstraints().add(simpleColumnNumber, forCompetitorColumn);

                ColumnConstraints forFightNumberColumn = new ColumnConstraints(FIGHT_NUMBER_COLUMN_WIDTH);
                forFightNumberColumn.setFillWidth(true);
                bracketGridPane.getColumnConstraints().add(simpleColumnNumber + 1, forFightNumberColumn);

                ColumnConstraints forDecorationColumn = new ColumnConstraints(DECORATION_COLUMN_WIDTH);
                forDecorationColumn.setFillWidth(true);
                bracketGridPane.getColumnConstraints().add(simpleColumnNumber + 2, forDecorationColumn);
            });

        int bracketWinnerXPos = bracket.getColumns().get( bracket.getColumns().size() -1 ).getFights().get(0).getWinner().getCoordinates().getX();

        ColumnConstraints forBracketWinnerColumn = new ColumnConstraints(COMPETITOR_COLUMN_WIDTH);
        forBracketWinnerColumn.setFillWidth(true);
        bracketGridPane.getColumnConstraints().add(bracketWinnerXPos, forBracketWinnerColumn);

        // draw a 0-fight column
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

                Label labelNumber = new Label(String.valueOf(fight.getNumber()));
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

        // draw the rest of the fight columns
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

                        Label labelNumber = new Label(String.valueOf(fight.getNumber()));
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
