package ru.academits.ageev.view;

import javax.swing.*;
import java.awt.*;

public class View {
    private int row;
    private int column;

    public View() {
        JFrame frame = new JFrame("Minesweeper");
        frame.setSize(680, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Menu menu = new Menu();
        frame.add(menu, BorderLayout.NORTH);

        row = menu.getRowCount();
        column = menu.getColumnCount();

        frame.add(new Field(row, column), BorderLayout.CENTER);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}

