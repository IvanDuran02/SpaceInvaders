package com.invaders.spaceinvaders;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SpaceInvadersApp extends Application {

    private Pane root = new Pane(); // Canvas
    private Parent createContext() {
        root.setPrefSize(600, 800); // Canvas size

        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {
    stage.setScene(new Scene(createContext()));
    stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
