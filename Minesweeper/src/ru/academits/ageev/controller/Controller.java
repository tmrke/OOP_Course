package ru.academits.ageev.controller;

import ru.academits.ageev.model.Model;
import ru.academits.ageev.model.ModelInterface;
import ru.academits.ageev.view.Cage;
import ru.academits.ageev.view.View;
import ru.academits.ageev.view.ViewInterface;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;


public class Controller {
    ModelInterface model = new Model();
    ViewInterface view = new View(model);

    public void startProgram() {
        clickNewGame();
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
