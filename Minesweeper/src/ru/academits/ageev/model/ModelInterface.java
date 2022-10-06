package ru.academits.ageev.model;

import javax.swing.*;
import java.util.ArrayList;

public interface ModelInterface {

    ArrayList<Cell> getCellList();

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

    void openWithoutBombZone(Cell cell);

    int getAround3x3BombCount(Cell cell);

    void stopTimer();

    Timer getNewTimer();

    void restartTimer();

    String getTimeString();
}
