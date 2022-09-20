package ru.academits.ageev.model;

public interface ModelInterface {
    String[] getScales();

    String getConvertValue(String inputValueString, String inputScale, String outputScale);

    double getOutputValue(String inputValueString, String outputValueString, double inputValue);
}
