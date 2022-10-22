package ru.academits.ageev.minesweeper_controller;

import ru.academits.ageev.minesweeper_model.Model;
import ru.academits.ageev.minesweeper_view.View;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {
    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        view.start(getActionListenerList());
        startTimer();
    }

    private void startTimer() {
        Timer timer = model.getNewTimer();

        timer.addActionListener(e -> {
            view.setTime(model.getTimeString());

            if (model.winGame()) {
                timer.stop();
            }
        });

        timer.start();
    }

    private List<ActionListener> getActionListenerList() {
        return new ArrayList<>(Arrays.asList(
                getNewGameButtonActionListener(),
                getHighScoreButtonActionListener(),
                getExitButtonActionListener()
        ));
    }

    private ActionListener getNewGameButtonActionListener() {
        return e -> {
            view.setField(
                    model.getSizeBySizeString(view.getSelectedSizeString()),
                    model.getNewCellList(view.getSelectedSizeString()));

            view.setSizeFrame(view.getSelectedSizeString());
            model.restartTimer();
            view.setFlagsCount(model.getBombsCount());

            view.clickToCell();
        };
    }

    public ActionListener getExitButtonActionListener() {
        return e -> System.exit(0);
    }

    private ActionListener getHighScoreButtonActionListener() {
        return e -> SwingUtilities.invokeLater(view::clickOnHighScoreButton);
    }
}