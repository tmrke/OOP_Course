package ru.academits.ageev.minesweeper_view;

import ru.academits.ageev.minesweeper_model.Cell;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public interface View {
    void start(List<ActionListener> actionListenerList);

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

    void clickToCell();

    void clickOnHighScoreButton();
}
