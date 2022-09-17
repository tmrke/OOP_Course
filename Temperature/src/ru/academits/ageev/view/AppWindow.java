package ru.academits.ageev.view;

import javax.swing.*;
import java.awt.*;

public class AppWindow {
    private final JTextField leftTextField = new JTextField("25", 12);
    private final JTextField rightTextField = new JTextField(12);
    private final JComboBox<String> leftComboBox = new JComboBox<>(new String[]{
            "Fahrenheit",
            "Celsius",
            "Kelvin"
    });
    private final JComboBox<String> rightComboBox = new JComboBox<>(new String[]{
            "Fahrenheit",
            "Celsius",
            "Kelvin"
    });
    private final JButton convertButton = new JButton("convert");

    public AppWindow() {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Temperature convertor");
            window.setSize(600, 400);
            window.setVisible(true);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            window.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();

            constraints.gridx = 0;
            constraints.gridy = 0;
            window.add(new JLabel("Input temperature"), constraints);

            constraints.gridx = 0;
            constraints.gridy = 1;
            window.add(leftTextField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 2;
            window.add(leftComboBox, constraints);

            constraints.gridx = 2;
            constraints.gridy = 1;
            window.add(convertButton, constraints);

            constraints.gridx = 4;
            constraints.gridy = 0;
            window.add(new JLabel("Result"), constraints);

            constraints.gridx = 4;
            constraints.gridy = 1;
            window.add(rightTextField, constraints);

            constraints.gridx = 4;
            constraints.gridy = 2;
            window.add(rightComboBox, constraints);
        });
    }

    public JComboBox<String> getLeftComboBox() {
        return leftComboBox;
    }

    public JComboBox<String> getRightComboBox() {
        return rightComboBox;
    }

    public JButton getConvertButton() {
        return convertButton;
    }

    public String getLeftTextFieldValue() {
        return leftTextField.getText();
    }

    public void setRightTextFieldValue(String value) {
        rightTextField.setText(value);
    }
}
