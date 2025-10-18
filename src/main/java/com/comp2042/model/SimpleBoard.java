package com.comp2042.model;

import com.comp2042.GameConfig;
import com.comp2042.model.bricks.Brick;
import com.comp2042.model.bricks.BrickGenerator;
import com.comp2042.model.bricks.RandomBrickGenerator;

import java.awt.*;

public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;
    // Level System
    private int level = 1;
    private int totalRowCleared = 0;

    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        //currentGameMatrix = new int[height][width];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }


    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }

    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(GameConfig.BRICK_SPAWN_X, GameConfig.BRICK_SPAWN_Y);   // generating point of bricks changed from 10
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0));
    }

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public ClearRow clearRows() {   // need to check (doesnt mean rows are cleared every time this function called)
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        int numCleared = clearRow.getLinesRemoved();
        boolean isLeveledUp = false;
        if (numCleared > 0) {
            totalRowCleared += numCleared;
            int newLevel = 1 + totalRowCleared/GameConfig.ROWS_PER_LEVEL;
            if (newLevel > this.level) {
                isLeveledUp = true;
                this.level = newLevel;
            }
        }

        currentGameMatrix = clearRow.getNewMatrix();
        // Return a new ClearRow object with the correct data and the flag
        return new ClearRow(numCleared, currentGameMatrix, clearRow.getScoreBonus(), isLeveledUp);
    }

    @Override
    public int getLevel() { return this.level; }

    @Override
    public Score getScore() {
        return score;
    }

    // 5 column will be embbed by some bricks
    public void initializeWithObstacles() {
        // place obstacles to the bottom of the gamescreen
        for (int y = 20; y < 25 && y < width; y++) {
            for (int x = 0; x < width && x < height; x++) {
                if (Math.random() < 0.6) {
                    currentGameMatrix[y][x] = 8;  // 8th bricks for obstacle
                }
            }
        }
    }

    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        //currentGameMatrix = new int[height][width];
        score.reset();
        this.level = 1;
        this.totalRowCleared = 0;
        createNewBrick();
    }
}
