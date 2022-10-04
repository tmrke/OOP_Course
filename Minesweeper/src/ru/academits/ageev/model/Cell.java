package ru.academits.ageev.model;

public class Cell {
    private boolean isOpen;
    private boolean isBomb;
    private boolean isMarkedBomb;
    private final int index;
    private int aroundBombsCount;

    public Cell(int index) {
        this.index = index;
    }

    public int getAroundBombsCount() {
        return aroundBombsCount;
    }

    public boolean isOpen() {
        return isOpen;
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

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setAroundBombsCount(int aroundBombsCount) {
        this.aroundBombsCount = aroundBombsCount;
    }
}
