package com.app.kt_bracket.ui_controller;

import com.app.kt_bracket.data.Category;
import com.app.kt_bracket.display.BracketDisplayer;
import com.app.kt_bracket.drawing.BracketDrawer;
import com.app.kt_bracket.drawing.CategoryListDrawer;
import com.app.kt_bracket.exporters.SpreadsheetExporter;
import com.app.kt_bracket.importers.TextFileImporter;
import com.app.kt_bracket.logic.BracketBuilder;
import com.app.kt_bracket.logic.Numberer;
import com.app.kt_bracket.structure.Competitor;
import com.app.kt_bracket.structure.Mat;
import com.app.kt_bracket.tools.Helper;
import com.app.kt_bracket.tools.Zoomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
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

    @Autowired
    Helper helper;

    @Autowired
    Zoomer zoomer;

    @Autowired
    BracketDisplayer bracketDisplayer;

    @FXML
    public Group bracketPaneGroup;

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

    private List<Category> categories = new ArrayList<>();
    private Mat mat;
    GridPane currentBracketGridPane;


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
                    .ifPresentOrElse(bracket -> {
                        currentBracketGridPane = bracket.getLayoutRepresentation();
                        bracketDisplayer.display(bracket, bracketPaneGroup, zoomer);
                    }, () -> bracketDisplayer.clear(bracketPaneGroup) );
            }
            else bracketDisplayer.clear(bracketPaneGroup);
        });
    }

    public void importCategoryItemAction(ActionEvent actionEvent)
    {
        if ( textFileImporter.importCategory(categories) )
        {
            categoryListDrawer.draw(categories, categoriesTreeTableView);
            categoriesTreeTableView.getSelectionModel().selectFirst();
        }
    }

    public void importCategoriesItemAction(ActionEvent actionEvent)
    {
        if ( textFileImporter.importCategories(categories) )
        {
            categoryListDrawer.draw(categories, categoriesTreeTableView);
            categoriesTreeTableView.getSelectionModel().selectFirst();
        }
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
        zoomer.resetBracketGridPaneScale();
    }

    public void buildAllItemAction(ActionEvent actionEvent)
    {
        mat = new Mat(categories.stream()
                .map(category -> bracketBuilder.build(category)).collect(Collectors.toList()));
        numberer.number(mat);
        categoryListDrawer.drawSortedAfterNumbering(categories, mat, categoriesTreeTableView);
        bracketDrawer.drawAll(mat);
        categoriesTreeTableView.getSelectionModel().selectFirst();
    }

    public void zoomInItemAction(ActionEvent actionEvent)
    {
        zoomer.zoomIn(currentBracketGridPane);
    }

    public void zoomOutItemAction(ActionEvent actionEvent)
    {
        zoomer.zoomOut(currentBracketGridPane);
    }

    public void closeItemAction(ActionEvent actionEvent)
    {
        System.exit(0);
    }

    public void manualMenuItemAction(ActionEvent actionEvent)
    {
        helper.showUsersManual();
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
