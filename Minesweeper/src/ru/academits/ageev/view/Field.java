package ru.academits.ageev.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Field extends JPanel {
    public Field(Integer[] size, ArrayList<Cage> cageList) {
        GridLayout layout = new GridLayout(size[0], size[1]);
        this.setLayout(layout);

        for (Cage cage : cageList) {
            this.add(cage);
        }

        this.setVisible(true);
    }
}
