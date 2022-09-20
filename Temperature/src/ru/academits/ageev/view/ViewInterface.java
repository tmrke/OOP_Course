package ru.academits.ageev.view;

import javax.swing.*;

public interface ViewInterface {
    String getInputScaleString();

    String getOutputScaleString();

    JButton getConvertButton();

    String getLeftTextFieldValue();

    void setRightTextFieldValue(String valueString);
}
