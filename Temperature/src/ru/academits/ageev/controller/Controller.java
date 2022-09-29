package ru.academits.ageev.controller;

import ru.academits.ageev.model.ModelInterface;
import ru.academits.ageev.view.View;

import java.awt.event.ActionListener;

public class Controller {
    private final View guiView;
    private final ModelInterface model;

    public Controller(ModelInterface model, View guiView) {
        this.model = model;
        this.guiView = guiView;
    }

    public void start() {
        guiView.start(model, getActionListener());
    }

    private ActionListener getActionListener() {
        return e -> {
            try {
                double inputValue = Double.parseDouble(guiView.getInputTextFieldValue());
                double outputValue = model.getOutputValue(guiView.getInputScale(), guiView.getOutputScale(), inputValue);

                guiView.setOutputTextFieldValue(outputValue);
            } catch (NumberFormatException ex) {
                guiView.showMessageDialog();
            }
        };
    }
}