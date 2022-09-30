package ru.academits.ageev.controller;

import ru.academits.ageev.model.Model;
import ru.academits.ageev.model.ModelInterface;
import ru.academits.ageev.view.Cage;
import ru.academits.ageev.view.GuiView;
import ru.academits.ageev.view.View;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Controller {
    ModelInterface model = new Model();
    View view = new GuiView(model);

    public void startProgram() {
        clickToHighScore();
        clickNewGame();
        clickToAbout();
        clickToCage();
        clickExit();
    }

    public ActionListener clickNewGame() {
        //TODO при новой игре не активны кнопки поля

        return e -> {
            view.setField(
                    model.getSizeBySizeString(view.getMenu().getSelectedSizeString()),
                    model.getNewCageList(view.getMenu().getSelectedSizeString()));

            view.setSizeFrame(view.getMenu().getSelectedSizeString());
            view.getMenu().restartTimer();
            view.getMenu().setFlagCountLabel(model.getFlagCount());
        };
    }

    public void clickExit() {
        view.clickExit(e -> System.exit(0));
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
                    frameAbout.setSize(400, 150);
                    frameAbout.setVisible(true);

                    JTextArea aboutTextArea = new JTextArea();
                    StringBuilder stringBuilder = new StringBuilder();

                    try {
                        Scanner scanner = new Scanner(new FileInputStream("Minesweeper/src/ru/academits/ageev/resources/about.txt"));
                        while (scanner.hasNextLine()) {
                            stringBuilder.append(scanner.nextLine()).append("\n");
                        }
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                    aboutTextArea.append(String.valueOf(stringBuilder));
                    aboutTextArea.setSize(300, 200);
                    frameAbout.add(aboutTextArea);
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
