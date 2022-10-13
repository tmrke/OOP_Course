package ru.academits.ageev.minesweeper_model;

import javax.swing.*;
import java.util.ArrayList;

public interface ModelInterface {
    ArrayList<Cell> getNewCageList(String sizeString);

    Integer[][] getAllSizes();

    String getSizeStringBySize(Integer[] sizes);

    int getFlagsCount();

    void setFlagsCount(int flagsCount);

    int getMarkedBombsCount();

    void setMarkedBombsCount(int markedBombsCount);

    String[] getSizesString();

    Integer[] getSizeBySizeString(String sizeString);

    boolean winGame();

    int getBombsCount();

    int getAround3x3BombCount(Cell cell);

    boolean is3x3AreaClear(Cell cell);

    void scanField3x3CellList(Cell cell);

    int getAround3x3FlagCount(Cell cell);

    void stopTimer();

    Timer getNewTimer();

    void restartTimer();

    String getTimeString();

    void openWithoutBombCells(Cell cell);
}
