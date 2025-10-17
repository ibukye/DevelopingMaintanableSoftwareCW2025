package com.comp2042.model;

public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;
    private final boolean isLeveledUp;

    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus, boolean isLeveledUp) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
        this.isLeveledUp = isLeveledUp;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    public int getScoreBonus() {
        return scoreBonus;
    }

    public boolean getIsLeveledUp() { return isLeveledUp; }
}
