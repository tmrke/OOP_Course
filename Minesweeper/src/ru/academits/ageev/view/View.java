package ru.academits.ageev.view;

import ru.academits.ageev.model.Cell;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface View {
    void start(ArrayList<ActionListener> actionListenerList);

    void setTime(String timeString);

    String getSelectedSizeString();

    Menu getMenu();

    void setField(Integer[] size, ArrayList<Cell> cellList);

    void setSizeFrame(String sizeFrame);

    void setFlagsCount(int bombsCount);

    void clickExit(ActionListener actionListener);

    void clickHighScore(ActionListener actionListener);

    void clickNewGame(ActionListener actionListener);

    void clickToAbout();

    void clickToCage();

    void clickOnHighScoreButton();
}
