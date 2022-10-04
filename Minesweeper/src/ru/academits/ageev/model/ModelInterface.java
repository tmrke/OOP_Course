package ru.academits.ageev.model;

import java.util.ArrayList;

public interface ModelInterface {

    ArrayList<Cell> getCellList();

    ArrayList<Cell> getNewCageList(String sizeString);

    int getFlagsCount();

    void setFlagsCount(int flagsCount);

    int getMarkedBombsCount();

    void setMarkedBombsCount(int markedBombsCount);

    String[] getSizesString();

    Integer[] getSizeBySizeString(String sizeString);

    boolean winGame();

    int getBombCount();

    void openWithoutBombZone(Cell cell);
}
