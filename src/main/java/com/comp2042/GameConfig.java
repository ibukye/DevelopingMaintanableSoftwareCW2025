package com.comp2042;

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
}