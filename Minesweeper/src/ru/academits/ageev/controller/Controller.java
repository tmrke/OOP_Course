package ru.academits.ageev.controller;

import ru.academits.ageev.model.GameRecordsReader;
import ru.academits.ageev.model.Model;
import ru.academits.ageev.model.ModelInterface;
import ru.academits.ageev.view.GuiView;
import ru.academits.ageev.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Controller {
    private final ModelInterface model = new Model();
    private final View view = new GuiView(model);

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
                    model.getSizeBySizeString(view.getSelectedSizeString()),
                    model.getNewCageList(view.getSelectedSizeString()));

            view.setSizeFrame(view.getSelectedSizeString());
            view.getMenu().restartTimer();
            view.getMenu().setFlagsCountLabel(model.getBombCount());

            view.clickToCage();
        };
    }

    public ActionListener getExitButtonActionListener() {
        return e -> System.exit(0);
    }

    private ActionListener getHighScoreButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            GameRecordsReader gameRecordsReader = new GameRecordsReader();
            String[] results = gameRecordsReader.getGameRecords();

            JFrame gameRecordsList = new JFrame("High score");
            gameRecordsList.setSize(400, 320);
            gameRecordsList.setDefaultCloseOperation(EXIT_ON_CLOSE);

            GridLayout layout = new GridLayout(10, 0, 10, 10);
            JPanel grid = new JPanel();
            grid.setLayout(layout);

            for (String result : results) {
                JLabel label = new JLabel(result);
                grid.add(label);
            }

            gameRecordsList.add(grid);
            gameRecordsList.setSize(250, 400);
            gameRecordsList.setVisible(true);

            view.getMenu().getHighScoresButton().setVisible(true);
        });
    }
}