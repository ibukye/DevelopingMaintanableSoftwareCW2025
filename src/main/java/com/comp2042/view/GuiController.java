package com.comp2042.view;

import com.comp2042.controller.*;
import com.comp2042.model.DownData;
import com.comp2042.model.ViewData;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

    @FXML
    private GridPane gamePanel;
    @FXML
    private Group groupNotification;
    @FXML
    private GridPane brickPanel;
    @FXML
    private GameOverPanel gameOverPanel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label highScoreLabel;
    @FXML
    private GridPane nextBrickPanel;
    @FXML
    private Button pauseButton;
    @FXML
    private Button resumeButton;

    private Rectangle[][] displayMatrix;
    private InputEventListener eventListener;
    private Rectangle[][] rectangles;
    private Rectangle[][] nextBrickRectangles;
    private Image pauseImg;
    private Image resumeImg;
    private MediaPlayer clearRowSoundPlayer;
    private MediaPlayer speedUpSoundPlayer;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);

        // load icons
        try {
            pauseImg = new Image(getClass().getResourceAsStream("/icons/pauseButton.png"));
            resumeImg = new Image(getClass().getResourceAsStream("/icons/resumeButton.png"));
            ImageView pauseIcon = new ImageView(pauseImg);
            ImageView resumeIcon = new ImageView(resumeImg);
            pauseIcon.setFitWidth(16);
            resumeIcon.setFitWidth(16);
            pauseIcon.setPreserveRatio(true);
            resumeIcon.setPreserveRatio(true);
            pauseButton.setGraphic(pauseIcon);
            resumeButton.setGraphic(resumeIcon);
        } catch (Exception e) {
            System.err.println("Failed to load icon img: " + e.getMessage());
        }

        try {
            URL clearResource = getClass().getResource("/sound/clearRowSound.mp3");
            URL speedResource = getClass().getResource("/sound/speedUpSound.mp3");
            Media clearMedia = new Media(clearResource.toString());
            Media speedMedia = new Media(speedResource.toString());
            clearRowSoundPlayer = new MediaPlayer(clearMedia);
            speedUpSoundPlayer = new MediaPlayer(speedMedia);
        } catch (Exception e) {
            System.err.println("Failed to load the sound file" + e.getMessage());
        }


        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        /*gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
            }
        });*/
        /*InputHandler inputHandler = new InputHandler(eventListener, this);  // pass the role to InputHandler
        gamePanel.setOnKeyPressed(inputHandler);*/

        gameOverPanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        // nextBrick panel initializetion (4x4)
        nextBrickRectangles = new Rectangle[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Rectangle rectangle = new Rectangle(12, 12);
                nextBrickRectangles[i][j] = rectangle;
                // Place to the panel (component, x, y)
                nextBrickPanel.add(rectangle, j, i);
            }
        }



        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);

        // Core of the game
        /*timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();*/
    }

    private void displayNextBrick(int[][] nextBrick)  {
        // Need to initialize the panel to not overwrite
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                nextBrickRectangles[i][j].setFill(Color.TRANSPARENT);
            }
        }
        for (int i = 0; i < nextBrick.length; i++) {
            for (int j = 0; j < nextBrick[i].length; j++) {
                if (nextBrick[i][j] != 0) setRectangleData(nextBrick[i][j], nextBrickRectangles[i][j]);
            }
        }
    }

    public void updateHighScore(int score) {
        highScoreLabel.setText(String.valueOf(score));
    }

    // For down event
    public void updateScreen(DownData downData) {
        if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
            clearRowSoundPlayer.stop();
            clearRowSoundPlayer.play();
            int obtainedScore = downData.getClearRow().getScoreBonus();
            NotificationPanel notificationPanel = new NotificationPanel("+" + obtainedScore);
            groupNotification.getChildren().add(notificationPanel);
            notificationPanel.showScore(groupNotification.getChildren());
        }

        // Level Up
        if (downData.getClearRow() != null && downData.getClearRow().getIsLeveledUp()) {
            speedUpSoundPlayer.stop();
            speedUpSoundPlayer.play();
            NotificationPanel speedUpPanel = new NotificationPanel("Speed UP!");
            groupNotification.getChildren().add(speedUpPanel);
            speedUpPanel.showScore(groupNotification.getChildren());
        }

        ViewData viewData = downData.getViewData();
        displayNextBrick(viewData.getNextBrickData());
        refreshBrick(downData.getViewData());
        gamePanel.requestFocus();
    }


    private Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            case 8 -> Color.GRAY;
            default -> Color.WHITE;
        };
    }


    public void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
        }
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    //
    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
        /*    if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();*/
            updateScreen(downData);
        }
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
        // Fix: "this.gameController" is null in GameController.java
        // Create an instance of InputHandler for sure
        InputHandler inputHandler = new InputHandler(this.eventListener, this);
        gamePanel.setOnKeyPressed(inputHandler);
    }

    public void bindScore(IntegerProperty integerProperty) {
        scoreLabel.textProperty().bind(integerProperty.asString());
    }

    // call a function to stop the execution
    public void gameOver() {
        //timeLine.stop();
        //GameController.stopGame();
        pauseGame(null);
        pauseButton.setVisible(false);
        resumeButton.setVisible(false);
        eventListener.stopGame();   // call from interface (Separation of Concerns)
        gameOverPanel.setVisible(true);
        // save the score
        eventListener.saveScore(eventListener.getCurrentScore());
        isGameOver.setValue(Boolean.TRUE);
    }

    public void newGame(ActionEvent actionEvent) {
        eventListener.stopGame();   // Chamged from timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        //eventListener.resumeGame(); // changed from timeLine.play()
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
        pauseButton.setVisible(true);
    }

    public void pauseGame(ActionEvent actionEvent) {
        eventListener.stopGame();
        isPause.set(true);
        pauseButton.setVisible(false);
        resumeButton.setVisible(true);
        gamePanel.requestFocus();
    }

    public void resumeGame(ActionEvent actionEvent) {
        eventListener.resumeGame();
        isPause.set(false);
        resumeButton.setVisible(false);
        pauseButton.setVisible(true);
        gamePanel.requestFocus();
    }

    public boolean isGamePaused() { return isPause.get(); }
    public boolean isGameOver() { return isGameOver.get(); }
}
