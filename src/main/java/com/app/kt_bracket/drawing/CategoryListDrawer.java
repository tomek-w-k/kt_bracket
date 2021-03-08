package com.app.kt_bracket.drawing;

import com.app.kt_bracket.data.Category;
import com.app.kt_bracket.structure.Competitor;
import com.app.kt_bracket.structure.Mat;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class CategoryListDrawer
{
    public void draw(List<Category> categories, TreeTableView<Competitor> categoriesTreeTableView)
    {
        List<TreeItem> categoryTreeItems = categories.stream()
            .map(category -> {
                List<TreeItem> competitorTreeItems = category.getCompetitorList().stream()
                    .map(competitor -> new TreeItem(competitor)).collect(Collectors.toList());

                TreeItem categoryTreeItem = new TreeItem(new Competitor(category.getName()));
                categoryTreeItem.getChildren().addAll(competitorTreeItems);
                return categoryTreeItem;
            }).collect(Collectors.toList());

        TreeItem categoriesRootItem = new TreeItem();
        categoriesRootItem.getChildren().addAll(categoryTreeItems);
        categoriesRootItem.setExpanded(true);
        categoriesTreeTableView.setRoot(categoriesRootItem);
    }

    public void drawSortedAfterNumbering(List<Category> categories, Mat mat, TreeTableView<Competitor> categoriesTreeTableView)
    {
        // sort categories to display them in the order corresponding to order of brackets in the mat
        List<Category> unsortedCategories = new ArrayList<>(categories);
        categories.clear();
        mat.getBrackets().stream()
            .forEach(bracket -> categories.add(unsortedCategories.stream()
                .filter(category -> category.getName().equals(bracket.getCategoryName()))
                .findAny()
                .orElseThrow()
            ));

        categories.stream()
            .forEach(category -> category.getCompetitorList().removeIf(competitor -> competitor.isEmpty()));

        this.draw(categories, categoriesTreeTableView);
    }

    public boolean remove(int index, List<Category> categories, TreeTableView<Competitor> categoriesTreeTableView, Mat mat)
    {
        int selectedIndex = categoriesTreeTableView.getSelectionModel().getSelectedIndex();

        if ( !categories.isEmpty() )
        {
            if ( mat != null && !mat.getBrackets().isEmpty() )
                if ( mat.getBrackets().size() > selectedIndex )
                    mat.getBrackets().remove(selectedIndex);

            categories.remove(selectedIndex);
            this.draw(categories, categoriesTreeTableView);

            if ( selectedIndex == categories.size() )
                categoriesTreeTableView.getSelectionModel().selectLast();

            categoriesTreeTableView.getSelectionModel().select(selectedIndex);
            return true;
        }
        else return false;
    }

    public void clearAll(List<Category> categories, TreeTableView<Competitor> categoriesTreeTableView, Mat mat)
    {
        if ( mat != null && !mat.getBrackets().isEmpty() )
            mat.getBrackets().clear();

        categoriesTreeTableView.setRoot(null);
        categories.clear();
    }
}
