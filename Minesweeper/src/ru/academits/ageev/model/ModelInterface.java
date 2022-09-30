package ru.academits.ageev.model;

import ru.academits.ageev.view.Cage;

import java.util.ArrayList;

public interface ModelInterface {

    ArrayList<Cage> getCageList();

    ArrayList<Cage> getNewCageList(String sizeString);

    int getFlagCount();

    void setFlagCount(int flagCount);

    int getMarkedBombCount();

    void setMarkedBombCount(int markedBombCount);

    String[] getSizesString();

    Integer[] getSizeBySizeString(String sizeString);

    boolean winGame();

    void openWithoutBombZone(Cage cage);
}
