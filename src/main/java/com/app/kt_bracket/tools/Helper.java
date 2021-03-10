package com.app.kt_bracket.tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;


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

    public void showUsersManual()
    {
        Parent manualMainLayout = null;
        FXMLLoader layoutLoader = new FXMLLoader();

        try {
            layoutLoader.setLocation(Helper.class.getResource("/manual.fxml"));
            manualMainLayout = layoutLoader.load();
        } catch (IOException e) { e.printStackTrace(); }

        Scene scene = new Scene(manualMainLayout, 800, 800);
        Stage manualWindow = new Stage();
        manualWindow.setScene(scene);
        manualWindow.initModality(Modality.WINDOW_MODAL);
        manualWindow.setTitle("KtBracket - User's manual");
        manualWindow.showAndWait();
    }
}
