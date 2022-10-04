package ru.academits.ageev.model;

import java.util.ArrayList;

public interface ModelInterface {

    ArrayList<Cell> getCellList();

    ArrayList<Cell> getNewCageList(String sizeString);

    int getFlagCount();

    void setFlagCount(int flagCount);

    int getMarkedBombCount();

    void setMarkedBombCount(int markedBombCount);

    String[] getSizesString();

    Integer[] getSizeBySizeString(String sizeString);

    boolean winGame();

    void openWithoutBombZone(Cell cell);
}
