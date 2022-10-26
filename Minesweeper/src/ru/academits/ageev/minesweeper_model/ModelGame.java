package ru.academits.ageev.minesweeper_model;

import javax.swing.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

public class ModelGame implements Model {
    private ArrayList<Cell> cellList;
    private final HashMap<String, Integer[]> sizesHashMap;
    private int flagsCount = 10;
    private int markedBombsCount;
    private final Integer[][] allSizes = {{9, 9, 10}, {16, 16, 40}, {16, 30, 100}};
    private String selectedSizeString = getSizeStringBySize(allSizes[0]);
    private Timer timer;
    private long lastTickTime = System.currentTimeMillis();

    public ModelGame() {
        sizesHashMap = new LinkedHashMap<>();                                  //Везде [0] это координата по X, кроме sizeHashMap, тут наоборот
        sizesHashMap.put(getSizeStringBySize(allSizes[0]), allSizes[0]);       //[0] = Y
        sizesHashMap.put(getSizeStringBySize(allSizes[1]), allSizes[1]);       //[1] = X
        sizesHashMap.put(getSizeStringBySize(allSizes[2]), allSizes[2]);       //[2] = количество бобм
    }

    @Override
    public Integer[][] getAllSizes() {
        return allSizes;
    }

    @Override
    public String getSizeStringBySize(Integer[] sizes) {
        return sizes[1] + " x " + sizes[0];
    }

    @Override
    public int getFlagsCount() {
        return flagsCount;
    }

    @Override
    public void setFlagsCount(int flagsCount) {
        this.flagsCount = flagsCount;
    }

    @Override
    public int getMarkedBombsCount() {
        return markedBombsCount;
    }

    @Override
    public void setMarkedBombsCount(int markedBombsCount) {
        this.markedBombsCount = markedBombsCount;
    }

    @Override
    public String[] getSizesString() {
        return sizesHashMap.keySet().toArray(new String[0]);
    }

    @Override
    public Integer[] getSizeBySizeString(String sizeString) {
        return sizesHashMap.get(sizeString);
    }

    @Override
    public ArrayList<Cell> getNewCellList(String sizeString) {
        Integer[] rowAndColumnSize = new Integer[2];

        if (sizeString.equals(getSizeStringBySize(allSizes[0]))) {
            rowAndColumnSize = getSizeBySizeString(sizeString);
        } else if (sizeString.equals(getSizeStringBySize(allSizes[1]))) {
            rowAndColumnSize = getSizeBySizeString(sizeString);
        } else if (sizeString.equals(getSizeStringBySize(allSizes[2]))) {
            rowAndColumnSize = getSizeBySizeString(sizeString);
        }

        int cellListSize = rowAndColumnSize[0] * rowAndColumnSize[1];

        flagsCount = getBombsCount();
        selectedSizeString = sizeString;
        cellList = new ArrayList<>(cellListSize);

        for (int i = 0; i < cellListSize; i++) {
            cellList.add(new Cell(i));
        }

        generateBomb(cellListSize);

        return cellList;
    }

    @Override
    public boolean winGame() {
        int bombCount = getBombsCount();
        return getMarkedBombsCount() == bombCount && flagsCount == 0;
    }

    @Override
    public int getBombsCount() {
        return sizesHashMap.get(selectedSizeString)[2];
    }

    private void generateBomb(int cellListSize) {
        Random random = new Random();

        for (int i = 0; i < getBombsCount(); i++) {
            int bombIndex = random.nextInt(cellListSize);

            if (!cellList.get(bombIndex).isBomb()) {
                cellList.get(bombIndex).setBomb(true);
            } else {
                i--;
            }
        }
    }

    private int[] getClickCellCoordinate(int index) {
        Integer[] currentSizes = sizesHashMap.get(selectedSizeString);
        int weight = currentSizes[1];

        int[] coordinate = new int[2];

        coordinate[0] = (index + weight) % weight + 1;
        coordinate[1] = index / weight + 1;

        return coordinate;
    }

    private int getClickCellIndex(int[] coordinate) {
        Integer[] size = getSizeBySizeString(selectedSizeString);

        return coordinate[1] * size[1] - (size[1] - coordinate[0]) - 1;
    }

    @Override
    public int getAround3x3BombCount(Cell cell) {
        int bombCountAround = 0;
        int[] coordinate = getClickCellCoordinate(cell.getIndex());

        Integer[] size = getSizeBySizeString(selectedSizeString);
        int sizeX = size[1];
        int sizeY = size[0];

        int startX = coordinate[0] - 1;
        int endX = coordinate[0] + 1;

        if (startX < 1) {
            startX = 1;
        }

        if (endX > sizeX) {
            endX = sizeX;
        }

        int startY = coordinate[1] - 1;
        int endY = coordinate[1] + 1;

        if (startY < 1) {
            startY = 1;
        }

        if (endY > sizeY) {
            endY = sizeY;
        }

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                int index = getClickCellIndex(new int[]{i, j});

                if (cellList.get(index).isBomb()) {
                    bombCountAround++;
                }
            }
        }

        return bombCountAround;
    }

    @Override
    public boolean is3x3AreaClear(Cell cell) {
        int[] coordinate = getClickCellCoordinate(cell.getIndex());

        Integer[] size = getSizeBySizeString(selectedSizeString);
        int sizeX = size[1];
        int sizeY = size[0];

        int startX = coordinate[0] - 1;
        int endX = coordinate[0] + 1;

        if (startX < 1) {
            startX = 1;
        }

        if (endX > sizeX) {
            endX = sizeX;
        }

        int startY = coordinate[1] - 1;
        int endY = coordinate[1] + 1;

        if (startY < 1) {
            startY = 1;
        }

        if (endY > sizeY) {
            endY = sizeY;
        }

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                int index = getClickCellIndex(new int[]{i, j});

                Cell currentCell = cellList.get(index);

                if (currentCell.isBomb() && !currentCell.isMarked()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void scanField3x3CellList(Cell cell) {
        int[] coordinate = getClickCellCoordinate(cell.getIndex());

        Integer[] size = getSizeBySizeString(selectedSizeString);
        int sizeX = size[1];
        int sizeY = size[0];

        int startX = coordinate[0] - 1;
        int endX = coordinate[0] + 1;

        if (startX < 1) {
            startX = 1;
        }

        if (endX > sizeX) {
            endX = sizeX;
        }

        int startY = coordinate[1] - 1;
        int endY = coordinate[1] + 1;

        if (startY < 1) {
            startY = 1;
        }

        if (endY > sizeY) {
            endY = sizeY;
        }

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                int index = getClickCellIndex(new int[]{i, j});

                if (index != cell.getIndex()) {
                    cellList.get(index).setOpen(true);
                }
            }
        }
    }

    @Override
    public int getAround3x3FlagCount(Cell cell) {
        int flagCountAround = 0;
        int[] coordinate = getClickCellCoordinate(cell.getIndex());

        Integer[] size = getSizeBySizeString(selectedSizeString);
        int sizeX = size[1];
        int sizeY = size[0];

        int startX = coordinate[0] - 1;
        int endX = coordinate[0] + 1;

        if (startX < 1) {
            startX = 1;
        }

        if (endX > sizeX) {
            endX = sizeX;
        }

        int startY = coordinate[1] - 1;
        int endY = coordinate[1] + 1;

        if (startY < 1) {
            startY = 1;
        }

        if (endY > sizeY) {
            endY = sizeY;
        }

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                int index = getClickCellIndex(new int[]{i, j});

                if (cellList.get(index).isMarked()) {
                    flagCountAround++;
                }
            }
        }

        return flagCountAround;
    }

    @Override
    public void stopTimer() {
        timer.stop();
    }

    @Override
    public Timer getNewTimer() {
        timer = new Timer(0, e -> getTimeString());
        timer.start();

        return timer;
    }

    @Override
    public void restartTimer() {
        lastTickTime = System.currentTimeMillis();

        timer = new Timer(100, e -> getTimeString());
        timer.start();
    }

    @Override
    public String getTimeString() {
        long runningTime = System.currentTimeMillis() - lastTickTime;
        Duration duration = Duration.ofMillis(runningTime);

        long hours = duration.toHours();
        duration = duration.minusHours(hours);

        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);

        long millis = duration.toMillis();
        long seconds = millis / 1000;

        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public void openWithoutBombCells(Cell cell) {
        if (cell.isBomb() || cell.isMarked() || cell.isOpen()) {
            return;
        }

        cell.setAroundBombsCount(getAround3x3BombCount(cell));
        cell.setOpen(true);

        if (cell.getAroundBombsCount() > 0) {
            return;
        }

        int[] coordinate = getClickCellCoordinate(cell.getIndex());
        int x = coordinate[0];
        int y = coordinate[1];

        Integer[] size = getSizeBySizeString(selectedSizeString);
        int sizeX = size[1];
        int sizeY = size[0];

        int borderFromX = x - 1;
        int borderToX = x + 1;

        if (x == 1 || borderFromX < 1) {
            borderFromX = 1;
        } else if (x == sizeX || borderToX > sizeX) {
            borderToX = sizeX;
        }

        int borderFromY = y - 1;
        int borderToY = y + 1;

        if (y == 1 || borderFromY < 1) {
            borderFromY = 1;
        } else if (y == sizeY || borderToY > sizeY) {
            borderToY = sizeY;
        }

        for (int i = borderFromX; i <= borderToX; i++) {
            for (int j = borderFromY; j <= borderToY; j++) {
                int currentIndex = getClickCellIndex(new int[]{i, j});
                Cell currentCell = cellList.get(currentIndex);

                openWithoutBombCells(currentCell);
            }
        }
    }
}