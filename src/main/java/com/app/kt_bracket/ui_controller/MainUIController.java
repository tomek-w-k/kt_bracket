package com.app.kt_bracket.ui_controller;

import com.app.kt_bracket.data.Category;
import com.app.kt_bracket.drawing.BracketDrawer;
import com.app.kt_bracket.drawing.CategoryListDrawer;
import com.app.kt_bracket.exporters.SpreadsheetExporter;
import com.app.kt_bracket.importers.TextFileImporter;
import com.app.kt_bracket.logic.BracketBuilder;
import com.app.kt_bracket.logic.CategoryBuilder;
import com.app.kt_bracket.logic.Numberer;
import com.app.kt_bracket.structure.Competitor;
import com.app.kt_bracket.structure.Mat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class MainUIController
{
    @Autowired
    BracketBuilder bracketBuilder;

    @Autowired
    BracketDrawer bracketDrawer;

    @Autowired
    CategoryListDrawer categoryListDrawer;

    @Autowired
    TextFileImporter textFileImporter;

    @Autowired
    SpreadsheetExporter spreadsheetExporter;

    @Autowired
    Numberer numberer;

    @FXML
    GridPane bracketGridPane;

    @FXML
    private TreeTableView<Competitor> categoriesTreeTableView;

    @FXML
    private TreeTableColumn categoryColumn;

    @FXML
    public Label categoryNameBracketLabel;

    @FXML
    public Label competitorsNumberBracketLabel;

    List<Category> categories = new ArrayList<>();
    Mat mat;
    double bracketGridPaneScale = 1;

    @FXML
    public void initialize()
    {
        try {
            categoryColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("fullName"));
        }
        catch(NullPointerException e) { e.printStackTrace(); }
        catch(IllegalStateException e) { e.printStackTrace(); }

        categoriesTreeTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, competitorTreeItem, t1) -> {
            if ( mat != null && t1 != null)
            {
                mat.findBracketByCategoryName( t1.getValue().getFullName() )
                    .ifPresentOrElse(bracket -> {
                        categoryNameBracketLabel.setText(bracket.getCategoryName());
                        competitorsNumberBracketLabel.setText("Competitors: " + t1.getChildren().size());
                        bracketDrawer.draw(bracket, bracketGridPane);
                    },
                    () -> {
                        categoryNameBracketLabel.setText("No bracket generated for this item");
                        competitorsNumberBracketLabel.setText("");
                        bracketDrawer.clear(bracketGridPane);
                    });

                this.bracketGridPaneScale = 1;
                this.bracketGridPane.setScaleX(this.bracketGridPaneScale);
                this.bracketGridPane.setScaleY(this.bracketGridPaneScale);
                bracketGridPane.setTranslateX( ((this.bracketGridPane.getWidth() - this.bracketGridPane.getWidth() * bracketGridPaneScale) / 2)  * -1 );
                bracketGridPane.setTranslateY( ((this.bracketGridPane.getHeight() - this.bracketGridPane.getHeight() * bracketGridPaneScale) / 2)   * -1 );
            }
            else categoryNameBracketLabel.setText("No bracket generated for this item");
        });
    }

    public void importCategoryItemAction(ActionEvent actionEvent)
    {
        textFileImporter.importCategory(categories);
        categoryListDrawer.draw(categories, categoriesTreeTableView);
    }

    public void importCategoriesItemAction(ActionEvent actionEvent)
    {
        textFileImporter.importCategories(categories);
        categoryListDrawer.draw(categories, categoriesTreeTableView);
    }

    public void exportItemAction(ActionEvent actionEvent)
    {
        spreadsheetExporter.exportToXls(this.mat);
    }

    public void removeSelectedCategoryItemAction(ActionEvent actionEvent)
    {

    }

    public void clearAllItemAction(ActionEvent actionEvent)
    {

    }

    public void buildAllItemAction(ActionEvent actionEvent)
    {
        mat = new Mat(categories.stream()
                .map(category -> bracketBuilder.build(category)).collect(Collectors.toList()));
        numberer.number(mat);
        categoryListDrawer.drawSortedAfterNumbering(categories, mat, categoriesTreeTableView);
    }

    public void zoomInItemAction(ActionEvent actionEvent)
    {
        bracketGridPaneScale = bracketGridPaneScale + 0.05;
        this.bracketGridPane.setScaleX(this.bracketGridPaneScale);
        this.bracketGridPane.setScaleY(this.bracketGridPaneScale);

        bracketGridPane.setTranslateX( ((this.bracketGridPane.getWidth() - this.bracketGridPane.getWidth() * bracketGridPaneScale) / 2)  * -1 );
        bracketGridPane.setTranslateY( ((this.bracketGridPane.getHeight() - this.bracketGridPane.getHeight() * bracketGridPaneScale) / 2)  * -1 );
    }

    public void zoomOutItemAction(ActionEvent actionEvent)
    {
        bracketGridPaneScale = bracketGridPaneScale - 0.05;
        this.bracketGridPane.setScaleX(this.bracketGridPaneScale);
        this.bracketGridPane.setScaleY(this.bracketGridPaneScale);

        bracketGridPane.setTranslateX( ((this.bracketGridPane.getWidth() - this.bracketGridPane.getWidth() * bracketGridPaneScale) / 2)  * -1 );
        bracketGridPane.setTranslateY( ((this.bracketGridPane.getHeight() - this.bracketGridPane.getHeight() * bracketGridPaneScale) / 2)   * -1 );
    }

    public void closeItemAction(ActionEvent actionEvent)
    {

    }
}
