package com.comp2042.controller;

import com.comp2042.GameConfig;
import com.comp2042.model.*;
import com.comp2042.view.GuiController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import com.comp2042.model.Difficulty;

import java.io.*;
import java.nio.file.Paths;

// Implemented from an abstract class (InputEventListener)
public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(GameConfig.BOARD_HEIGHT, GameConfig.BOARD_WIDTH);
    private final GuiController viewGuiController;
    private final Difficulty difficulty;
    private Timeline timeLine;
    private boolean isGameOver = false;
    private final String highScoreFilePath;

    public GameController(GuiController c, Difficulty difficulty) {
        this.difficulty = difficulty;
        this.board = new SimpleBoard(GameConfig.BOARD_HEIGHT, GameConfig.BOARD_WIDTH);
        viewGuiController = c;
        viewGuiController.setEventListener(this);


        board.createNewBrick();
        // if the difficulty is hard -> initialize the game screen with some obstacles
        if (this.difficulty == Difficulty.HARD) { board.initializeWithObstacles(); }
        // preparation of game screen
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        // need to render here
        viewGuiController.refreshGameBackground(board.getBoardMatrix());

        // Set a safe file path in the user's home directory
        String homeDir = System.getProperty("user.home");
        this.highScoreFilePath = Paths.get(homeDir, "tetris_highscore.txt").toString();

        viewGuiController.bindScore(board.getScore().scoreProperty());

        // Load and display the high score when the game starts
        viewGuiController.updateHighScore(loadScore());

        gameLoop();

        // Moved from GuiController
        // Manages the game progress
        /*timeLine = new Timeline(new KeyFrame(
                Duration.millis(GameConfig.GAME_SPEED_MS),
                //ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
                ae -> { // onDownEvent (cannot call moveDown since its private)
                    DownData downData = onDownEvent(new MoveEvent(EventType.DOWN, EventSource.THREAD));
                    // Need to reflect the change to the gui
                    viewGuiController.updateScreen(downData);
                }
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();*/
    }

    private void gameLoop() {
        if (timeLine != null) timeLine.stop();  //
        int speed = GameConfig.getSpeed(this.difficulty, board.getLevel());
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(speed),
                ae -> gameTick()
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    // Every 1 frame
    private void gameTick() {
        // if gameOver then stop
        if (viewGuiController.isGameOver()) { timeLine.stop(); return; }
        DownData downData = onDownEvent(new MoveEvent(EventType.DOWN, EventSource.THREAD));
        viewGuiController.updateScreen(downData);
        // if level up occur
        if (downData.getClearRow() != null && downData.getClearRow().getIsLeveledUp()) {
            gameLoop(); // start game loop with new speed
        }
    }

    @Override
    public int getCurrentScore() { return board.getScore().scoreProperty().get(); }

    @Override
    public int loadScore() {
        File file = new File(highScoreFilePath);
        // if there is no record
        if (!file.exists()) { return 0; }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            return Integer.parseInt(line);
        } catch (IOException e) {
            System.err.println("Failed to load high score: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public void saveScore(int score) {
        int prevHighScore = loadScore();
        if (prevHighScore < score) {
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(highScoreFilePath));
                writer.println(score);
                writer.close();
                // update the display as well
                viewGuiController.updateHighScore(score);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());

        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    @Override   // same as the others
    public void stopGame() {
        timeLine.stop();
    }

    @Override
    public void resumeGame() {
        timeLine.play();
    }

    @Override
    public void createNewGame() {
        board.newGame();
        if (this.difficulty == Difficulty.HARD) {
            board.initializeWithObstacles();
        }
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        gameLoop();
    }
}
