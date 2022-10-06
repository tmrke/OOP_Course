package ru.academits.ageev.controller;

import ru.academits.ageev.model.Model;
import ru.academits.ageev.model.ModelInterface;
import ru.academits.ageev.view.GuiView;
import ru.academits.ageev.view.View;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    private final ModelInterface model = new Model();
    private final View view = new GuiView(model);

    public void start() {
        view.start(getActionListenerList());
        startTimer();
    }

    private void startTimer() {
        Timer timer = model.getNewTimer();

        timer.addActionListener(e -> view.setTime(model.getTimeString()));
        timer.addActionListener(e -> {
            if (model.winGame()) {
                timer.stop();
            }
        });

        timer.start();
    }

    public ArrayList<ActionListener> getActionListenerList() {
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
                    model.getNewCageList(view.getSelectedSizeString()));

            view.setSizeFrame(view.getSelectedSizeString());
            model.restartTimer();
            view.setFlagsCount(model.getBombsCount());

            view.clickToCage();
        };
    }

    public ActionListener getExitButtonActionListener() {
        return e -> System.exit(0);
    }

    private ActionListener getHighScoreButtonActionListener() {
        return e -> SwingUtilities.invokeLater(view::clickOnHighScoreButton);
    }
}