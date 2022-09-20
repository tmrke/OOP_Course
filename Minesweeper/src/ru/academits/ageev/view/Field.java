package ru.academits.ageev.view;

import javax.swing.*;
import java.awt.*;

public class Field extends JPanel {
    public Field(int row, int column) {
        GridLayout layout = new GridLayout(row, column);
        this.setLayout(layout);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                Cage cage = new Cage();
                cage.setSize(10, 10);
                this.add(cage);
            }
        }

        this.setVisible(true);
    }
}
