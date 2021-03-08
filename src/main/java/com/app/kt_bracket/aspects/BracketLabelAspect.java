package com.app.kt_bracket.aspects;

import com.app.kt_bracket.structure.Bracket;
import com.app.kt_bracket.ui_controller.MainUIController;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class BracketLabelAspect
{
    @Autowired
    MainUIController mainUIController;


    @After("execution(* com.app.kt_bracket.drawing.BracketDrawer.draw(..)) && args(bracket,..)")
    public void onBracketDrawing(Bracket bracket)
    {
        String categoryName = bracket.getCategoryName();

        mainUIController.getCategoryNameBracketLabel().setText(categoryName);
        mainUIController.getCategories().stream()
            .filter(category -> category.getName().equals(categoryName))
            .findAny()
            .ifPresent( category -> mainUIController.getCompetitorsNumberBracketLabel().setText("Competitors: " + category.getCompetitorList().size()) );
    }

    @After("execution(* com.app.kt_bracket.drawing.BracketDrawer.clear(..))")
    public void onBracketGridPaneClearing()
    {
        mainUIController.getCategoryNameBracketLabel().setText("No bracket generated for this item");
        mainUIController.getCompetitorsNumberBracketLabel().setText("");
    }
}
