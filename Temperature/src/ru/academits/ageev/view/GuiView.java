package ru.academits.ageev.view;

import ru.academits.ageev.model.ModelInterface;
import ru.academits.ageev.model.Scale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GuiView implements View {
    private JTextField leftTextField;
    private JTextField rightTextField;
    private JComboBox<Scale> inputScaleComboBox;
    private JComboBox<Scale> outputScaleComboBox;
    private JButton converterButton;

    @Override
    public void start(ModelInterface model, ActionListener actionListener){
        SwingUtilities.invokeLater(() -> {
            leftTextField = new JTextField("25.5", 12);
            rightTextField = new JTextField(12);

            inputScaleComboBox = new JComboBox<>(model.getScalesList().toArray(new Scale[0]));
            outputScaleComboBox = new JComboBox<>(model.getScalesList().toArray(new Scale[0]));

            JFrame window = new JFrame("Temperature converter");
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

            converterButton = new JButton("convert");
            converterButton.addActionListener(actionListener);
            constraints.gridx = 2;
            constraints.gridy = 1;
            window.add(converterButton, constraints);

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
    public Scale getInputScale() {
        return (Scale) inputScaleComboBox.getSelectedItem();
    }

    @Override
    public Scale getOutputScale() {
        return (Scale) outputScaleComboBox.getSelectedItem();
    }

    @Override
    public String getInputTextFieldValue() {
        return leftTextField.getText();
    }

    @Override
    public void setOutputTextFieldValue(double value) {
        rightTextField.setText(String.valueOf(value));
    }

    @Override
    public void showMessageDialog() {
        JOptionPane.showMessageDialog(converterButton, "The temperature value should be in the form of a number");
    }
}