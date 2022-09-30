package ru.academits.ageev.controller;

import ru.academits.ageev.model.Model;
import ru.academits.ageev.model.ModelInterface;
import ru.academits.ageev.view.GuiView;
import ru.academits.ageev.view.View;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    ModelInterface model = new Model();
    View view = new GuiView(model);

    public void start() {
        view.start(getActionListenerList());
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
                    model.getSizeBySizeString(view.getMenu().getSelectedSizeString()),
                    model.getNewCageList(view.getMenu().getSelectedSizeString()));

            view.setSizeFrame(view.getMenu().getSelectedSizeString());
            view.getMenu().restartTimer();
            view.getMenu().setFlagCountLabel(model.getFlagCount());
        };
    }

    public ActionListener getExitButtonActionListener() {
        return e -> System.exit(0);
    }

    private ActionListener getHighScoreButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            try {
                JFrame highScoreFrame = new GameRecords(true);
                highScoreFrame.setVisible(true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
