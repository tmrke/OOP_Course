package ru.academits.ageev.model;

public class CellModel {
    private boolean isBomb;
    private boolean isMarkedBomb;
    private final int index;

    public CellModel(int index) {
        this.index = index;
        isBomb = false;
        isMarkedBomb = false;
    }

    public int getIndex() {
        return index;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean condition) {
        isBomb = condition;
    }

    public boolean isMarkedBomb() {
        return isMarkedBomb;
    }

    public void setMarkedBomb(boolean condition) {
        isMarkedBomb = condition;
    }
}
