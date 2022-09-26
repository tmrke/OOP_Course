package ru.academits.ageev.view;

import javax.swing.*;

public class Cage extends JButton {
    private boolean isBomb;
    private boolean isMarkedBomb;

    private final int index;

    public Cage(int index) {
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
