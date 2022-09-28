package ru.academits.ageev.controller;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameRecords extends JFrame {
    private final boolean visible;
    JFrame gameRecordsList = new JFrame("High Score");
    JPanel grid = new JPanel();
    private final String[] results = new String[10];

    public GameRecords(boolean visible) throws IOException {

        this.visible = visible;
        setSize(400, 320);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GridLayout layout = new GridLayout(10, 0, 10, 10);
        grid.setLayout(layout);

        for (int i = 0; i < 10; i++) {
            if (getResults()[i] == null) {
                grid.add(new JLabel(i + 1 + " position: - "));
            } else {
                grid.add(new JLabel(i + 1 + " position: " + getResults()[i]));
            }
        }

        gameRecordsList.add(grid);
        gameRecordsList.setSize(250, 400);

        setVisible(visible);
    }


    public void setVisible(boolean visible) {
        gameRecordsList.setVisible(visible);
    }

    public boolean isVisible() {
        return visible;
    }

    public String[] getResults() throws IOException {
        final BufferedReader file = new BufferedReader(new FileReader("Minesweeper/src/ru/academits/ageev/resources/result.txt"));

        for (int i = 0; i < results.length; i++) {
            results[i] = file.readLine();
        }

        file.close();

        return results;
    }
}
