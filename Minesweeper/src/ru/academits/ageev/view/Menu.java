package ru.academits.ageev.view;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;

public class Menu {
    private final JPanel menu = new JPanel();
    private final JButton newGameButton = new JButton("new game");
    private JComboBox<String> fieldSizeComboBox;
    private final JButton aboutButton = new JButton("about");
    private final JButton highScoresButton = new JButton("high scores");
    private final JButton exitButton = new JButton("exit");
    private final JLabel flagCountLabel = new JLabel();

    private final JLabel timeResult = new JLabel("00:00");
    private Timer timer;
    private long lastTickTime = System.currentTimeMillis();

    public JPanel getMenuPanel(String[] sizes, int flagCount) {
        GridLayout gridLayout = new GridLayout(1, 8);
        menu.setLayout(gridLayout);

        fieldSizeComboBox = new JComboBox<>(sizes);

        menu.add(newGameButton);
        menu.add(fieldSizeComboBox);
        menu.add(aboutButton);
        menu.add(highScoresButton);
        menu.add(exitButton);

        flagCountLabel.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/resources/flag.png"));
        flagCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menu.add(flagCountLabel);
        flagCountLabel.setText(String.valueOf(flagCount));


        timeResult.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/resources/time.png"));
        timeResult.setHorizontalAlignment(SwingConstants.CENTER);
        menu.add(timeResult);

        timer = getNewTimer();

        return menu;
    }

    public void setFlagCountLabel(int flagCount) {
        flagCountLabel.setText(String.valueOf(flagCount));
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

    public String getTime() {
        timer.stop();
        return timeResult.getText();
    }

    public Timer getNewTimer() {
        Timer newTimer = new Timer(100, e -> createActionEventForTimer());
        newTimer.start();

        return newTimer;
    }

    public void restartTimer() {
        lastTickTime = System.currentTimeMillis();

        timer = new Timer(100, e -> createActionEventForTimer());
        timer.start();
    }

    private void createActionEventForTimer() {
        long runningTime = System.currentTimeMillis() - lastTickTime;
        Duration duration = Duration.ofMillis(runningTime);

        long hours = duration.toHours();
        duration = duration.minusHours(hours);

        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);

        long millis = duration.toMillis();
        long seconds = millis / 1000;

        String timeResultString = String.format("%02d:%02d", minutes, seconds);
        timeResult.setText(timeResultString);
    }

    public String getSelectedSizeString() {
        return (String) fieldSizeComboBox.getSelectedItem();
    }
}
