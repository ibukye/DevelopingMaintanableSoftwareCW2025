package com.comp2042.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GameOverPanel extends VBox {

    private Runnable onMainMenu;

    public GameOverPanel() {
        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");

        final Button mainMenuButton = new Button("Main Menu");

        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(gameOverLabel, mainMenuButton);

        mainMenuButton.setOnAction(event -> {
            onMainMenu.run();
        });
    }

    public void setMainMenu(Runnable action) {
        this.onMainMenu = action;
    }
}
