package com.comp2042.controller;

import com.comp2042.model.DownData;
import com.comp2042.model.ViewData;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

// handle key event of JavaFX
import javafx.event.EventHandler;

import com.comp2042.view.GuiController;

public class InputHandler implements EventHandler<KeyEvent> {
    // in between GuiController and GameController
    private final InputEventListener gameController;
    private final GuiController guiController;

    public InputHandler(InputEventListener gameController, GuiController guiController) {
        this.gameController = gameController;
        this.guiController = guiController;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        // Paused or GameOver?
        if (guiController.isGamePaused() || guiController.isGameOver()) {
            if (keyEvent.getCode() == KeyCode.N) { guiController.newGame(null); }   // New game if N pressed
            if (keyEvent.getCode() != KeyCode.SPACE && keyEvent.getCode() != KeyCode.N) {
                return;
            }
        }

        ViewData viewData = null;   // store updated (screen) data
        switch (keyEvent.getCode()) {   // GameController
            case LEFT:
                viewData = gameController.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER));
                break;
            case RIGHT:
                viewData = gameController.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER));
                break;
            case UP:
                viewData = gameController.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER));
                break;
            case DOWN:
                DownData downData = gameController.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.USER));
                // moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                guiController.updateScreen(downData);
                break;
            case SPACE:
                if (guiController.isGamePaused()) { guiController.resumeGame(null); }
                else { guiController.pauseGame(null); }
                break;
        }

        // After input, needs to be updated the screen
        if (viewData != null) {
            guiController.refreshBrick(viewData);
        }

        // empty the keyEvent buffer
        keyEvent.consume();
    }
}
