package ru.academits.ageev.model;

import ru.academits.ageev.view.Cage;
import ru.academits.ageev.view.Menu;
import ru.academits.ageev.view.View;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class Model implements ModelInterface {
    private ArrayList<Cage> cageList;
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
    public String[] getSizesString() {
        return sizesHashMap.keySet().toArray(new String[0]);
    }

    @Override
    public Integer[] getSizeBySizeString(String sizeString) {
        return sizesHashMap.get(sizeString);
    }

    @Override
    public ArrayList<Cage> getCageList() {
        return cageList;
    }

    @Override
    public ArrayList<Cage> getNewCageList(String sizeString) {
        switch (sizeString) {
            case "9 x 9", "16 x 16", "16 x 30" -> {
                Integer[] rowAndColumnSize = getSizeBySizeString(sizeString);
                int cageListSize = rowAndColumnSize[0] * rowAndColumnSize[1];
                flagCount = getBombCount(cageListSize);
                selectedSizeString = sizeString;

                cageList = new ArrayList<>(cageListSize);

                for (int i = 0; i < cageListSize; i++) {
                    cageList.add(new Cage(i));
                }

                generateBomb(cageListSize);
            }
        }

        return cageList;
    }

    @Override
    public void leftMouseClick(Cage cage, View view) {
        if (!cage.isEnabled()) {
            return;
        }

        if (cage.isBomb()) {
            cage.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/resources/bang.png"));
            JOptionPane.showMessageDialog(view.getField(), "Game over");
        } else {
            cage.setEnabled(false);
            openWithoutBombZone(cage);
        }

        int[] coordinate = getClickCageCoordinate(cage.getIndex());

        System.out.println(Arrays.toString(coordinate));
        System.out.println(getClickCageIndex(coordinate));
    }

    @Override
    public void rightMouseClick(Cage cage, Menu menu) throws IOException {
        if (!cage.isMarkedBomb()) {
            cage.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/resources/flag.png"));
            cage.setMarkedBomb(true);
            flagCount--;

            if (cage.isBomb()) {
                markedBombCount++;
            }

            if (winGame()) {
                String resultString = menu.getTime();
                ResultWriter resultWriter = new ResultWriter(resultString);
                resultWriter.addResult();

                JOptionPane.showMessageDialog(menu, "You win! Your result: " + resultString);
            }
        } else {
            cage.setIcon(null);
            cage.setMarkedBomb(false);
            flagCount++;

            if (cage.isBomb()) {
                markedBombCount--;
            }
        }

        menu.setFlagCountLabel(flagCount);
    }

    private boolean winGame() {
        return markedBombCount == getBombCount(getCageList().size());
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
            cageList.get(bombIndex).setBomb(true);
            System.out.println(bombIndex);
        }
    }

    private void openWithoutBombZone(Cage cage) {
        int[] clickCageCoordinate = getClickCageCoordinate(cage.getIndex());
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
                    Cage currentCage = cageList.get(currentIndex);

                    if (!currentCage.isBomb()) {
                        currentCage.setEnabled(false);

                        int bombCountAround = getAround3x3BombCount(currentCage);

                        if (bombCountAround != 0) {
                            currentCage.setText(String.valueOf(bombCountAround));
                        }
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

    private int getAround3x3BombCount(Cage cage) {
        int bombCountAround = 0;
        int[] coordinate = getClickCageCoordinate(cage.getIndex());

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

                if (cageList.get(index).isBomb()) {
                    bombCountAround++;
                }
            }
        }

        return bombCountAround;
    }
}