package com.app.kt_bracket.tools;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;


@Component
public class Helper
{
    public void showAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information dialog");
        ImageView iconView = new ImageView();
        iconView.setImage(new Image("icons/kt-bracket-60.png"));
        alert.setGraphic(iconView);
        alert.setHeaderText("KtBracket");

        String content = "Version 1.0\n" +
                        "Copyright \u00a9 2021 Tomek Wąsik \n\n\n" +
                        "Icons used in this app are from https://icons8.com";

        alert.setContentText(content);
        alert.showAndWait();
    }
}
