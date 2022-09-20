package ru.academits.ageev.controller;

import ru.academits.ageev.model.Model;
import ru.academits.ageev.model.ModelInterface;
import ru.academits.ageev.view.View;
import ru.academits.ageev.view.ViewInterface;

import javax.swing.*;

public class Controller {
    private final ViewInterface view;
    private final ModelInterface model;

    public Controller() {
        model = new Model();
        view = new View(model);
    }

    public void startProgram() {
        view.getConvertButton().addActionListener(action -> {
            try {
                String inputScale = view.getInputScaleString();
                String outputScale = view.getOutputScaleString();
                String inputValueString = view.getLeftTextFieldValue();

                String outputValueString = model.getConvertValue(inputValueString, inputScale, outputScale);
                view.setRightTextFieldValue(outputValueString);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view.getConvertButton(), "The temperature value should be in the form of a number");
            }
        });
    }
}