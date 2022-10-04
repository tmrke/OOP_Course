package ru.academits.ageev.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

public class Model implements ModelInterface {
    private ArrayList<Cell> cellList;
    private final HashMap<String, Integer[]> sizesHashMap;

    private int flagCount = 10;
    private int markedBombCount;
    private String selectedSizeString = "9 x 9";

    public Model() {
        sizesHashMap = new LinkedHashMap<>();
        sizesHashMap.put("9 x 9", new Integer[]{9, 9});
        sizesHashMap.put("16 x 16", new Integer[]{16, 16});
        sizesHashMap.put("16 x 30", new Integer[]{16, 30});     //Везде [0] это координата по X, кроме размеров в sizeHashMap, тут наоборот
    }

    @Override
    public int getFlagCount() {
        return flagCount;
    }

    @Override
    public void setFlagCount(int flagCount) {
        this.flagCount = flagCount;
    }

    @Override
    public int getMarkedBombCount() {
        return markedBombCount;
    }

    @Override
    public void setMarkedBombCount(int markedBombCount) {
        this.markedBombCount = markedBombCount;
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
    public ArrayList<Cell> getCellList() {
        return cellList;
    }

    @Override
    public ArrayList<Cell> getNewCageList(String sizeString) {
        switch (sizeString) {
            case "9 x 9", "16 x 16", "16 x 30" -> {
                Integer[] rowAndColumnSize = getSizeBySizeString(sizeString);
                int cageListSize = rowAndColumnSize[0] * rowAndColumnSize[1];

                flagCount = getBombCount(cageListSize);
                selectedSizeString = sizeString;
                cellList = new ArrayList<>(cageListSize);

                for (int i = 0; i < cageListSize; i++) {
                    cellList.add(new Cell(i));
                }

                generateBomb(cageListSize);
            }
        }

        return cellList;
    }

    @Override
    public boolean winGame() {
        return markedBombCount == getBombCount(getCellList().size());
    }

    private int getBombCount(int cageListSize) {
        int bombCount;
        if (cageListSize == 81) {
            bombCount = 10;
        } else if (cageListSize == 256) {
            bombCount = 40;
        } else {
            bombCount = 100;
        }

        return bombCount;
    }

    private void generateBomb(int cageListSize) {
        Random random = new Random();

        for (int i = 0; i < getBombCount(cageListSize); i++) {
            int bombIndex = random.nextInt(cageListSize);
            cellList.get(bombIndex).setBomb(true);
        }
    }

    @Override
    public void openWithoutBombZone(Cell cell) {
        int[] clickCageCoordinate = getClickCageCoordinate(cell.getIndex());
        int x = clickCageCoordinate[0];
        int y = clickCageCoordinate[1];

        Integer[] size = getSizeBySizeString(selectedSizeString);
        int sizeX = size[1];
        int sizeY = size[0];

        int offset = 1;
        boolean hasBomb = false;

        while (!hasBomb) {
            int borderFromX = x - offset;
            int borderToX = x + offset;

            if (x == 1 || borderFromX < 1) {
                borderFromX = 1;
            } else if (x == sizeX || borderToX > sizeX) {
                borderToX = sizeX;
            }

            int borderFromY = y - offset;
            int borderToY = y + offset;

            if (y == 1 || borderFromY < 1) {
                borderFromY = 1;
            } else if (y == sizeY || borderToY > sizeY) {
                borderToY = sizeY;
            }

            for (int i = borderFromX; i <= borderToX; i++) {
                for (int j = borderFromY; j <= borderToY; j++) {
                    int currentIndex = getClickCageIndex(new int[]{i, j});
                    Cell currentCell = cellList.get(currentIndex);

                    if (!currentCell.isBomb()) {
                        currentCell.setOpen(true);
                        currentCell.setAroundBombsCount(getAround3x3BombCount(currentCell));
                    } else {
                        hasBomb = true;
                    }
                }
            }

            offset++;
        }
    }

    private int[] getClickCageCoordinate(int index) {
        Integer[] currentSizes = sizesHashMap.get(selectedSizeString);
        int weight = currentSizes[1];

        int[] coordinate = new int[2];

        coordinate[0] = (index + weight) % weight + 1;
        coordinate[1] = index / weight + 1;

        return coordinate;
    }

    private int getClickCageIndex(int[] coordinate) {
        Integer[] size = getSizeBySizeString(selectedSizeString);

        return coordinate[1] * size[1] - (size[1] - coordinate[0]) - 1;
    }

    private int getAround3x3BombCount(Cell cell) {
        int bombCountAround = 0;
        int[] coordinate = getClickCageCoordinate(cell.getIndex());

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
                int index = getClickCageIndex(new int[]{i, j});

                if (cellList.get(index).isBomb()) {
                    bombCountAround++;
                }
            }
        }

        return bombCountAround;
    }
}