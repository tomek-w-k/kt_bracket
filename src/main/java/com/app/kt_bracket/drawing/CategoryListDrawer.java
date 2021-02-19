package com.app.kt_bracket.drawing;

import com.app.kt_bracket.data.Category;
import com.app.kt_bracket.structure.Competitor;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class CategoryListDrawer
{
    public void draw(List<Category> categories, TreeItem categoriesRootItem)
    {
        List<TreeItem> categoryTreeItems = categories.stream()
            .map(category -> {
                List<TreeItem> competitorTreeItems = category.getCompetitorList().stream()
                    .map(competitor -> new TreeItem(competitor)).collect(Collectors.toList());

                TreeItem categoryTreeItem = new TreeItem(new Competitor(category.getName()));
                categoryTreeItem.getChildren().addAll(competitorTreeItems);
                return categoryTreeItem;
            }).collect(Collectors.toList());

        categoriesRootItem.getChildren().addAll(categoryTreeItems);
    }
}
