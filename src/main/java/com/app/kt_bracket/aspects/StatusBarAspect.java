package com.app.kt_bracket.aspects;

import com.app.kt_bracket.ui_controller.MainUIController;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class StatusBarAspect
{
    @Autowired
    MainUIController mainUIController;


    @AfterReturning(pointcut = "execution(* com.app.kt_bracket.importers.TextFileImporter.*(..))", returning = "categoriesImported")
    public void onImport(boolean categoriesImported)
    {
        bracketsNeedRebuild(categoriesImported);
    }

    @After("execution(* com.app.kt_bracket.logic.BracketBuilder.build(..))")
    public void onBuild()
    {
        mainUIController.getMessageStatusBarLabel().setText("Brackets are up-to-date.");
    }

    @AfterReturning(pointcut = "execution(* com.app.kt_bracket.drawing.CategoryListDrawer.remove(..))", returning = "categoryRemoved")
    public void onRemove(boolean categoryRemoved)
    {
        bracketsNeedRebuild(categoryRemoved);
    }

    // - - - private methods - - -
    private void bracketsNeedRebuild(boolean stateChanged)
    {
        if (stateChanged)
            mainUIController.getMessageStatusBarLabel().setText("Brackets need to be rebuilt. Choose \"Build\" option.");

        mainUIController.getCategoriesStatusBarLabel().setText( "Categories: " + mainUIController.getCategories().size() );
    }
}
