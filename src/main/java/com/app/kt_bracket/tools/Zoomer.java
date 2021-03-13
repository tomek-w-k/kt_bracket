package com.app.kt_bracket.tools;

import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;


@Component
public class Zoomer
{
    private double bracketGridPaneScale = 1;


    public void updateZoom(GridPane bracketGridPane)
    {
        zoom(bracketGridPane);
    }

    public void zoomIn(GridPane bracketGridPane)
    {
        if ( bracketGridPane != null )
        {
            bracketGridPaneScale = bracketGridPaneScale + 0.05;
            zoom(bracketGridPane);
        }
    }

    public void zoomOut(GridPane bracketGridPane)
    {
        if ( bracketGridPane != null )
        {
            bracketGridPaneScale = bracketGridPaneScale - 0.05;
            zoom(bracketGridPane);
        }
    }

    private void zoom(GridPane bracketGridPane)
    {
        double currentZoomedWidth = bracketGridPane.getWidth() - bracketGridPane.getWidth() * bracketGridPaneScale;
        double currentZoomedHeight = bracketGridPane.getHeight() - bracketGridPane.getHeight() * bracketGridPaneScale;

        bracketGridPane.setScaleX(this.bracketGridPaneScale);
        bracketGridPane.setScaleY(this.bracketGridPaneScale);
        bracketGridPane.setTranslateX( (currentZoomedWidth / 2)  * -1 );
        bracketGridPane.setTranslateY( (currentZoomedHeight / 2)  * -1 );
    }

    public void resetBracketGridPaneScale()
    {
        this.bracketGridPaneScale = 1;
    }
}
