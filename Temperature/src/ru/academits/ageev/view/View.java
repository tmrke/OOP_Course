package ru.academits.ageev.view;

import ru.academits.ageev.model.Model;
import ru.academits.ageev.model.Scale;

import java.awt.event.ActionListener;

public interface View {
    void start(Model converter, ActionListener actionListener);

    Scale getInputScale();

    Scale getOutputScale();

    String getInputValueString();

    void setOutputValue(double valueString);

    void showErrorMessage();
}
