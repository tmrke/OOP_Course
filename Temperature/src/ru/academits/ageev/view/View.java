package ru.academits.ageev.view;

import ru.academits.ageev.model.ModelInterface;

import javax.swing.*;
import java.awt.*;

public class View implements ViewInterface {
    private final JTextField leftTextField = new JTextField("25.5", 12);
    private final JTextField rightTextField = new JTextField(12);
    private final JComboBox<String> inputScaleComboBox;
    private final JComboBox<String> outputScaleComboBox;
    private final JButton convertButton = new JButton("convert");

    public View(ModelInterface model) {
        inputScaleComboBox = new JComboBox<>(model.getScales());
        outputScaleComboBox = new JComboBox<>(model.getScales());

        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Temperature convertor");
            window.setSize(600, 400);
            window.setMinimumSize(new Dimension(450, 200));
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
            window.add(inputScaleComboBox, constraints);

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
            window.add(outputScaleComboBox, constraints);
        });
    }

    @Override
    public String getInputScaleString() {
        return (String) inputScaleComboBox.getSelectedItem();
    }

    @Override
    public String getOutputScaleString() {
        return (String) outputScaleComboBox.getSelectedItem();
    }

    @Override
    public JButton getConvertButton() {
        return convertButton;
    }

    @Override
    public String getLeftTextFieldValue() {
        return leftTextField.getText();
    }

    @Override
    public void setRightTextFieldValue(String valueString) {
        rightTextField.setText(valueString);
    }
}
