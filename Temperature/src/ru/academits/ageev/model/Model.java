package ru.academits.ageev.model;

import ru.academits.ageev.view.View;

import javax.swing.*;

public class Model {
    private final View view;

    public Model(View view) {
        this.view = view;
    }

    public void startProgram() {
        view.getWindow().getConvertButton().addActionListener(action -> {
            try {
                convert();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view.getWindow().getConvertButton(), "The temperature value should be in the form of a number");
            }
        });
    }

    public void convert() {
        double inputValue = Integer.parseInt(view.getWindow().getLeftTextFieldValue());

        String leftComboBoxString = (String) view.getWindow().getLeftComboBox().getSelectedItem();
        String rightComboBoxString = (String) view.getWindow().getRightComboBox().getSelectedItem();

        //noinspection ConstantConditions
        double outputValue = getOutputValue(leftComboBoxString, rightComboBoxString, inputValue);
        String outputValueString = Double.toString(outputValue);

        view.getWindow().setRightTextFieldValue(outputValueString);
    }

    public double getOutputValue(String left, String right, double inputValue) {
        String fahrenheit = "Fahrenheit";
        String celsius = "Celsius";
        String kelvin = "Kelvin";

        if (left.equals(celsius)) {
            if (right.equals(fahrenheit)) {
                return 1.8 * inputValue + 32;
            }

            if (right.equals(kelvin)) {
                return inputValue + 273;
            }
        }

        if (left.equals(fahrenheit)) {
            if (right.equals(celsius)) {
                return 5 * (inputValue - 32) / 9;
            }

            if (right.equals(kelvin)) {
                return (inputValue + 459) / 1.8;
            }
        }

        if (left.equals(kelvin)) {
            if (right.equals(celsius)) {
                return inputValue - 273;
            }

            if (right.equals(fahrenheit)) {
                return inputValue * 1.8 - 459;
            }
        }

        return inputValue;
    }
}