package com.app.kt_bracket.ui_controller;

import com.app.kt_bracket.data.Category;
import com.app.kt_bracket.drawing.BracketDrawer;
import com.app.kt_bracket.drawing.CategoryListDrawer;
import com.app.kt_bracket.exporters.SpreadsheetExporter;
import com.app.kt_bracket.importers.TextFileImporter;
import com.app.kt_bracket.logic.BracketBuilder;
import com.app.kt_bracket.logic.Numberer;
import com.app.kt_bracket.structure.Competitor;
import com.app.kt_bracket.structure.Mat;
import com.app.kt_bracket.tools.Helper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
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

    @Autowired
    Helper helper;

    @FXML
    GridPane bracketGridPane;

    @FXML
    private TreeTableView<Competitor> categoriesTreeTableView;

    @FXML
    private TreeTableColumn categoryColumn;

    @FXML
    private Label categoryNameBracketLabel;

    @FXML
    private Label competitorsNumberBracketLabel;

    @FXML
    private Label messageStatusBarLabel;

    @FXML
    private Label categoriesStatusBarLabel;

    List<Category> categories = new ArrayList<>();
    Mat mat;
    double bracketGridPaneScale = 1;

    @FXML
    public void initialize()
    {
        categoryListDrawer.clearAll(categories, categoriesTreeTableView, mat);

        try {
            categoryColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("fullName"));
        }
        catch(NullPointerException e) { e.printStackTrace(); }
        catch(IllegalStateException e) { e.printStackTrace(); }

        categoriesTreeTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, competitorTreeItem, t1) -> {
            if ( mat != null && t1 != null)
            {
                mat.findBracketByCategoryName( t1.getValue().getFullName() )
                    .ifPresentOrElse(bracket -> bracketDrawer.draw(bracket, bracketGridPane),
                                    () -> bracketDrawer.clear(bracketGridPane));

                this.bracketGridPaneScale = 1;
                this.bracketGridPane.setScaleX(this.bracketGridPaneScale);
                this.bracketGridPane.setScaleY(this.bracketGridPaneScale);
                bracketGridPane.setTranslateX( ((this.bracketGridPane.getWidth() - this.bracketGridPane.getWidth() * bracketGridPaneScale) / 2)  * -1 );
                bracketGridPane.setTranslateY( ((this.bracketGridPane.getHeight() - this.bracketGridPane.getHeight() * bracketGridPaneScale) / 2)   * -1 );
            }
            else bracketDrawer.clear(bracketGridPane);
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
        if ( categories.size() == 1 ) // when last element is being removed...
        {
            categoryListDrawer.clearAll(categories, categoriesTreeTableView, mat);
            return;
        }

        categoryListDrawer.remove(categoriesTreeTableView.getSelectionModel().getSelectedIndex(), categories, categoriesTreeTableView, mat);
    }

    public void clearAllItemAction(ActionEvent actionEvent)
    {
        categoryListDrawer.clearAll(categories, categoriesTreeTableView, mat);
    }

    public void buildAllItemAction(ActionEvent actionEvent)
    {
        mat = new Mat(categories.stream()
                .map(category -> bracketBuilder.build(category)).collect(Collectors.toList()));
        numberer.number(mat);
        categoryListDrawer.drawSortedAfterNumbering(categories, mat, categoriesTreeTableView);
        categoriesTreeTableView.getSelectionModel().selectFirst();
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

    public void aboutMenuItemAction(ActionEvent actionEvent)
    {
        helper.showAbout();
    }

    // - - - getters - - -
    public Label getCategoryNameBracketLabel()
    {
        return categoryNameBracketLabel;
    }

    public Label getCompetitorsNumberBracketLabel()
    {
        return competitorsNumberBracketLabel;
    }

    public Label getMessageStatusBarLabel()
    {
        return messageStatusBarLabel;
    }

    public Label getCategoriesStatusBarLabel()
    {
        return categoriesStatusBarLabel;
    }

    public List<Category> getCategories()
    {
        return categories;
    }
}
