package com.invaders.spaceinvaders;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class SpaceInvadersApp extends Application {

    private Pane root = new Pane(); // Canvas

    private double t = 0;

    private Sprite player = new Sprite(300, 750, 40, 40, "player", Color.BLUE); // player sprite

    private Parent createContent() {
        root.setPrefSize(600, 800); // Canvas size

        root.getChildren().add(player); // adds player onto canvas

        AnimationTimer timer = new AnimationTimer() { // smooth animations for player movement etc...
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        nextLevel();

        return root;
    }

    private void nextLevel() {
        for (int i = 0; i < 5; i++) {
            Sprite s = new Sprite(90 + i*100, 150, 30, 30, "enemy", Color.RED); // smaller than player, type enemy
            root.getChildren().add(s); // adds to level
        }
    }

    private List<Sprite> sprites() { // helper function to return sprites instead of nodes...
        return root.getChildren().stream().map(n -> (Sprite) n).collect(Collectors.toList()); //
    }

    private void update() {
        t += 0.016; // time is increased by 16 milliseconds

        sprites().forEach(s -> {
            switch(s.type) {

                case "enemybullet":
                    s.moveDown();

                    // if bullet collides with player...
                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.dead = true; // player dead
                        s.dead = true; // bullet dead
                    }
                    break;

                case "playerbullet":
                    s.moveUp();

                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true; // enemy dead
                            s.dead = true; // bullet dead
                        }
                    });
                    break;
                case "enemy":
                    if (t > 2) {
                        if (Math.random() < 0.3) { // random is 0 - 1 so this is 30% chance of shooting
                            shoot(s);
                        }
                    }
                    break;
            }
        });
        root.getChildren().removeIf(n -> { // removes if sprite is dead
            Sprite s = (Sprite) n;
            return s.dead;
        });

        if (t > 2) { // resets the counter
            t = 0;
        }
    }

    private void shoot(Sprite who) { // who shows location of person who shot the bullet
        // offset by 20px on x; type is set to who.type + "bullet" so for Ex. "player bullet" or "enemy bullet"
        Sprite s = new Sprite((int) who.getTranslateX() + 20, (int) who.getTranslateY(), 5, 20, who.type + "bullet", Color.BLACK);
        root.getChildren().add(s);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(e -> {
            // crazy enhanced switch case
            switch (e.getCode()) { // Controls player movement on key press
                case A -> player.moveLeft();
                case D -> player.moveRight();
                case SPACE -> shoot(player);
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    private static class Sprite extends Rectangle {
        boolean dead = false;
        final String type;

        Sprite(int x, int y, int w, int h, String type, Color color) { // Sprite class for player / enemies
            super(w, h, color);

            this.type = type; // Ex. type can be "player" | "enemy" etc..
            setTranslateX(x);
            setTranslateY(y);
        }

        void moveLeft() {
            setTranslateX(getTranslateX() - 5);
        }
        void moveRight() {
            setTranslateX(getTranslateX() + 5);
        }
        void moveUp() {
            setTranslateY(getTranslateY() - 5);
        }
        void moveDown() {
            setTranslateY(getTranslateY() + 5);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
