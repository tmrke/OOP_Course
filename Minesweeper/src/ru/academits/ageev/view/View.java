package ru.academits.ageev.view;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface View {
    Menu getMenu();

    void setField(Integer[] size, ArrayList<Cage> cageList);

    void setSizeFrame(String sizeFrame);

    Field getField();

    void clickExit(ActionListener actionListener);
}
