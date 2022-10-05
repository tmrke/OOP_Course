package ru.academits.ageev.controller;

import ru.academits.ageev.model.Model;
import ru.academits.ageev.view.View;

import java.awt.event.ActionListener;

public class Controller {
    private final View guiView;
    private final Model converter;

    public Controller(Model converter, View guiView) {
        this.converter = converter;
        this.guiView = guiView;
    }

    public void start() {
        guiView.start(converter, getActionListener());
    }

    private ActionListener getActionListener() {
        return e -> {
            try {
                double inputValue = Double.parseDouble(guiView.getInputValueString());
                double outputValue = converter.getConvertedTemperature(guiView.getInputScale(), guiView.getOutputScale(), inputValue);

                guiView.setOutputValue(outputValue);
            } catch (NumberFormatException ex) {
                guiView.showErrorMessage();
            }
        };
    }
}