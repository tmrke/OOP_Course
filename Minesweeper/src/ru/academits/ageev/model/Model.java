package ru.academits.ageev.model;

import ru.academits.ageev.view.Cage;
import ru.academits.ageev.view.Menu;
import ru.academits.ageev.view.ViewInterface;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Model implements ModelInterface {
    private ArrayList<Cage> cageList;
    private final HashMap<String, Integer[]> sizesHashMap;

    private int flagCount = 100;

    public Model() {
        sizesHashMap = new HashMap<>();

        sizesHashMap.put("16 x 16", new Integer[]{16, 16});
        sizesHashMap.put("16 x 30", new Integer[]{16, 30});
        sizesHashMap.put("9 x 9", new Integer[]{9, 9});
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
    public void leftMouseClick(Cage cage, ViewInterface view) {
        if (cage.isBomb()) {
            cage.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/resources/bang.png"));
            JOptionPane.showMessageDialog(view.getField(), "Game over");
        } else {
            cage.setEnabled(false);
        }
    }

    @Override
    public void rightMouseClick(Cage cage, Menu menu) {
        if (!cage.isMarkedBomb()) {
            cage.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/resources/flag.png"));
            cage.setMarkedBomb(true);
            flagCount--;
        } else {
            cage.setIcon(null);
            cage.setMarkedBomb(false);
            flagCount++;
        }

        menu.setFlagCountLabel(flagCount);
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
}