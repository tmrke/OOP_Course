package ru.academits.ageev.view;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.util.Objects;

public class Menu extends JPanel {
    private final JButton newGameButton = new JButton("new game");
    private final JComboBox<String> fieldSizeComboBox = new JComboBox<>(new String[]{"9 x 9",
//            "16 х 16"

    });
    private final JButton aboutButton = new JButton("about");
    private final JButton highScoresButton = new JButton("high scores");
    private final JButton exitButton = new JButton("exit");
    private final JLabel flagCountLabel = new JLabel("10");

    private final JLabel timeResult = new JLabel("00:00");
    private final Timer timer;
    private final long lastTickTime = System.currentTimeMillis();

    public Menu() {
        GridLayout gridLayout = new GridLayout(1, 8);
        this.setLayout(gridLayout);

        this.add(newGameButton);
        this.add(fieldSizeComboBox);
        this.add(aboutButton);
        this.add(highScoresButton);
        this.add(exitButton);

        flagCountLabel.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/resources/flag.png"));
        flagCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(flagCountLabel);

        timeResult.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/resources/time.png"));
        timeResult.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(timeResult);

        this.timer = new Timer(100, e -> {
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
        });

        timer.start();
    }

    public int getRowCount() {
        if (Objects.equals(fieldSizeComboBox.getSelectedItem(), "9 x 9")) {
            return 9;
        }

        return 16;
    }

    public int getColumnCount() {
        if (Objects.equals(fieldSizeComboBox.getSelectedItem(), "9 x 9")) {
            return 9;
        }

        return 16;
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

    public JLabel getFlagCountLabel() {
        return flagCountLabel;
    }

    public String getTime() {
        timer.stop();
        return timeResult.getText();
    }
}
