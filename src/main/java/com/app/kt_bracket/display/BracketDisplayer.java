package com.app.kt_bracket.display;

import com.app.kt_bracket.structure.Bracket;
import com.app.kt_bracket.tools.Zoomer;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;


@Component
public class BracketDisplayer
{
    public void display(Bracket bracket, Group bracketPaneGroup, Zoomer zoomer)
    {
        bracketPaneGroup.getChildren().clear();
        GridPane currentBracketGridPane = bracket.getLayoutRepresentation();
        bracketPaneGroup.getChildren().add(currentBracketGridPane);
        zoomer.updateZoom(currentBracketGridPane);
    }

    public void clear(Group bracketPaneGroup)
    {
        bracketPaneGroup.getChildren().clear();
    }
}
