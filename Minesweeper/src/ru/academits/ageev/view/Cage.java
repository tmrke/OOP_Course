package ru.academits.ageev.view;

import javax.swing.*;

public class Cage extends JButton {
    private boolean isBomb;
    private boolean isMarkedBomb;

    public Cage() {
        isBomb = false;
        isMarkedBomb = false;
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
