package com.comp2042;

import com.comp2042.model.Difficulty;

public final class GameConfig {
    // No need to create an instance for this just read from this file
    private GameConfig() {}

    // Board dimensions
    public static final int BOARD_HEIGHT = 25;
    public static final int BOARD_WIDTH = 10;

    // Game timing
    public static final int GAME_SPEED_MS = 400;

    // Brick properties
    public static final int BRICK_SPAWN_X = 4;
    public static final int BRICK_SPAWN_Y = 0;

    // LevelUp speed
    public static final int ROWS_PER_LEVEL = 5;

    // Change speed (90% of prev speed)
    public static int getSpeed(Difficulty difficulty, int level) {
        switch (difficulty) {
            case NORMAL:
            case HARD:
                int speed = (int) (500 * Math.pow(0.9, level)); // starts from 500ms
                // 0ms cannot play -> min at 100ms
                return Math.max(speed, 100);
            default:
                return 400;
        }

    }
}