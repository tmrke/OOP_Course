package ru.academits.ageev.view;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface View {
    void start(ActionListener actionListenerExit, ActionListener actionListenerHighScore, ActionListener actionListenerNewGame);

    Menu getMenu();

    void setField(Integer[] size, ArrayList<Cage> cageList);

    void setSizeFrame(String sizeFrame);
    void clickExit(ActionListener actionListener);

    void clickHighScore(ActionListener actionListener);

    void clickNewGame(ActionListener actionListener);

    void clickToAbout();
}
