package ru.academits.ageev.controller;

import ru.academits.ageev.model.Model;
import ru.academits.ageev.model.ModelInterface;
import ru.academits.ageev.view.Cage;
import ru.academits.ageev.view.View;
import ru.academits.ageev.view.ViewInterface;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Controller {
    ModelInterface model = new Model();
    ViewInterface view = new View(model);

    public void startProgram() {
        clickToHighScore();
        clickNewGame();
        clickToAbout();
        clickToCage();
        clickExit();
    }

    public void clickNewGame() {
        //TODO при новой игре не активны кнопки поля
        view.getMenu().getNewGameButton().addActionListener(e -> {
            view.setField(
                    model.getSizeBySizeString(
                            view.getMenu().getSelectedSizeString()),
                    model.getNewCageList(view.getMenu().getSelectedSizeString()));

            view.setSizeFrame(view.getMenu().getSelectedSizeString());
            view.getMenu().restartTimer();
            view.getMenu().setFlagCountLabel(model.getFlagCount());
        });
    }

    public void clickExit() {
        view.getMenu().getExitButton().addActionListener(e -> {
            System.exit(0);
        });
    }

    public void clickToHighScore() {
        view.getMenu().getHighScoresButton().addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    JFrame highScoreFrame = new GameRecords(true);
                    highScoreFrame.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });
    }

    public void clickToAbout() {
        view.getMenu().getAbout().addActionListener(e ->
                SwingUtilities.invokeLater(() -> {
                    JFrame frameAbout = new JFrame();
                    JLabel aboutLabel = new JLabel();
                    aboutLabel.setSize(250, 300);
                    frameAbout.setVisible(true);

                    //TODO прочитать файлы в столбик и вывести в Jpanel

                    StringBuilder stringBuilder = new StringBuilder();

                    try {
                        Scanner scanner = new Scanner(new FileInputStream("Minesweeper/src/ru/academits/ageev/resources/about.txt"));
                        while (scanner.hasNextLine()) {
                            stringBuilder.append(aboutLabel.getText()).append(System.lineSeparator()).append(scanner.nextLine());
                        }
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }


                    frameAbout.add(aboutLabel);
                }));
    }

    public void clickToCage() {
        ArrayList<Cage> cageList = model.getCageList();

        for (Cage cage : cageList) {
            cage.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        model.leftMouseClick(cage, view);
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        try {
                            model.rightMouseClick(cage, view.getMenu());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
        }
    }
}
