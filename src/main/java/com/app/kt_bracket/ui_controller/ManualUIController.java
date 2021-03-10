package com.app.kt_bracket.ui_controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.springframework.stereotype.Component;

import java.net.URL;


@Component
public class ManualUIController
{
    public WebView manualWebView;


    @FXML
    public void initialize()
    {
        URL url = this.getClass().getResource("/manual/html-export-test.html");
        WebEngine engine = manualWebView.getEngine();
        engine.load(url.toString());
    }
}
