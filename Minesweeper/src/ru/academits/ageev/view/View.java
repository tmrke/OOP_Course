package ru.academits.ageev.view;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface View {
    void start(ArrayList<ActionListener> actionListenerList);

    Menu getMenu();

    void setField(Integer[] size, ArrayList<Cell> cellList);

    void setSizeFrame(String sizeFrame);

    void clickExit(ActionListener actionListener);

    void clickHighScore(ActionListener actionListener);

    void clickNewGame(ActionListener actionListener);

    void clickToAbout();
}
