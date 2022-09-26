package ru.academits.ageev.view;

import java.util.ArrayList;

public interface ViewInterface {
    Menu getMenu();

    void setField(Integer[] size, ArrayList<Cage> cageList);

    void setSizeFrame(String sizeFrame);

    Field getField();
}
