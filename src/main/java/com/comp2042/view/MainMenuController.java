package com.comp2042.view;

import javafx.fxml.FXML;
//import java.awt.event.ActionEvent;
import javafx.event.ActionEvent;


public class MainMenuController {
    // field which have the reference to the Main Class
    private Main mainApp;

    // receives reference from Main class
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    // FXML code

    // Easy mode
    @FXML
    void onEasyClicked(ActionEvent e) {
        // create Main class
        System.out.println("Easy button was clicked!");
    }

    // Normal mode
    @FXML
    void onNormalClicked(ActionEvent e) {
        //
    }

    // Hard mode
    @FXML
    void onHardClicked(ActionEvent e) {
        //
    }

    // Setting Button
    @FXML
    void onSettingClicked(ActionEvent e) {
        //
    }

    // Exit Button
    @FXML
    void onExitClicked(ActionEvent e) {
        System.exit(0);
    }
}
