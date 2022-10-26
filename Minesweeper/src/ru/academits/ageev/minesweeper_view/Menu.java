package ru.academits.ageev.minesweeper_view;

import ru.academits.ageev.minesweeper_model.Model;

import javax.swing.*;
import java.awt.*;

public class Menu {
    private final Model model;
    private final JPanel menu = new JPanel();
    private final JButton newGameButton = new JButton("new game");
    private JComboBox<String> fieldSizeComboBox;
    private final JButton aboutButton = new JButton("about");
    private final JButton highScoresButton = new JButton("high scores");
    private final JButton exitButton = new JButton("exit");
    private final JLabel flagsCountLabel = new JLabel();

    private final JLabel timeResult = new JLabel("00:00");
    private Timer timer;

    public Menu(Model model) {
        this.model = model;
    }

    public JPanel getMenuPanel(String[] sizes, int flagCount) {
        GridLayout gridLayout = new GridLayout(1, 8);
        menu.setLayout(gridLayout);

        fieldSizeComboBox = new JComboBox<>(sizes);

        menu.add(newGameButton);
        menu.add(fieldSizeComboBox);
        menu.add(aboutButton);
        menu.add(highScoresButton);
        menu.add(exitButton);

        flagsCountLabel.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/minesweeper_resources/flag.png"));
        flagsCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menu.add(flagsCountLabel);
        flagsCountLabel.setText(String.valueOf(flagCount));

        timer = model.getNewTimer();

        timeResult.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/minesweeper_resources/time.png"));
        timeResult.setHorizontalAlignment(SwingConstants.CENTER);

        menu.add(timeResult);

        return menu;
    }

    public void setFlagsCountLabel(int flagCount) {
        flagsCountLabel.setText(String.valueOf(flagCount));
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JComboBox<String> getFieldSizeComboBox() {
        return fieldSizeComboBox;
    }

    public JButton getAbout() {
        return aboutButton;
    }

    public JButton getHighScoresButton() {
        return highScoresButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public String getSelectedSizeString() {
        return (String) fieldSizeComboBox.getSelectedItem();
    }

    public String getTime() {
        timer.stop();

        return timeResult.getText();
    }

    public JLabel getTimeResult() {
        return timeResult;
    }
}