package com.timothygeary;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

/** Aptly named class responsible for the Graphical User Interface */
public class GUI extends Application{
    @Override
    public void start(Stage stage) throws Exception{
        stage.setHeight( Screen.getPrimary().getBounds().getHeight() * 7 / 8 );
        stage.setWidth( Screen.getPrimary().getBounds().getWidth() / 2 );
        stage.setTitle("Graphing Loan Analyzer");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
