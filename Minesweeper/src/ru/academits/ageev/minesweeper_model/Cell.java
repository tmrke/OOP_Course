package ru.academits.ageev.minesweeper_model;

public class Cell {
    private boolean isOpen;
    private boolean isBomb;
    private boolean isMarked;
    private final int index;
    private int aroundBombsCount;

    public Cell(int index) {
        this.index = index;
    }

    public int getAroundBombsCount() {
        return aroundBombsCount;
    }

    public void setAroundBombsCount(int aroundBombsCount) {
        this.aroundBombsCount = aroundBombsCount;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean isBomb) {
        this.isBomb = isBomb;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean isMarked) {
        this.isMarked = isMarked;
    }

    public int getIndex() {
        return index;
    }
}
