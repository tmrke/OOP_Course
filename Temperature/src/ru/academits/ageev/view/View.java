package ru.academits.ageev.view;

import ru.academits.ageev.model.Scale;

import java.awt.event.ActionListener;

public interface View {
    void convert(ActionListener actionListener);

    Scale getInputScale();

    Scale getOutputScale();

    String getInputTextFieldValue();

    void setOutputTextFieldValue(double valueString);

    void showMessageDialog();
}
