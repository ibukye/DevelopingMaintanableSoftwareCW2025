package com.comp2042.view;

import com.comp2042.controller.GameController;
import com.comp2042.view.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
// For difficulty
import com.comp2042.model.Difficulty;

public class Main extends Application {

    // field for store Stage
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        /*URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();

        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, 300, 510);
        primaryStage.setScene(scene);
        primaryStage.show();
        new GameController(c);*/

        // load the pause and resume button assets
        /*Image pauseIcon = new Image(getClass().getResourceAsStream("icons/pauseButton.png"));
        Image resumeIcon = new Image(getClass().getResourceAsStream("icons/resumeButton.png"));
        primaryStage.getIcons().add(pauseIcon);
        primaryStage.getIcons().add(resumeIcon);
        */
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TetrisJFX");

        // Main Menu
        showMainMenu();
    }


    public void showMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MainMenu.fxml"));
            Parent root = loader.load();

            // pass the reference of the Main class to MainMenuController
            MainMenuController controller = loader.getController();
            controller.setMainApp(this);

            //primaryStage.setScene(new Scene(root, 300, 510));
            primaryStage.setScene(new Scene(root, 400, 510));
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Failed to load MainMenu.fxml");
            e.printStackTrace();
        }
    }

    public void showGameScreen(Difficulty difficulty) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gameLayout.fxml"));
            Parent root = loader.load();
            GuiController c = loader.getController();

            // create GameController and connect to GuiController
            new GameController(c, difficulty);

            //primaryStage.setScene(new Scene(root, 300, 510));
            primaryStage.setScene(new Scene(root, 400, 510));
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Failed to load gameLayout.fxml");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
