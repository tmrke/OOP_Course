package ru.academits.ageev.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model implements ModelInterface {
    private final List<String> scaleList;

    public Model() {
        scaleList = new ArrayList<>(Arrays.asList(
                "Fahrenheit",
                "Celsius",
                "Kelvin"));
    }

    @Override
    public String[] getScales() {
        return scaleList.toArray(new String[0]);
    }

    @Override
    public String getConvertValue(String inputValueString, String inputScale, String outputScale) {
        double inputValue = Double.parseDouble(inputValueString);
        double outputValue = getOutputValue(inputScale, outputScale, inputValue);

        return Double.toString(outputValue);
    }

    @Override
    public double getOutputValue(String inputScale, String outputScale, double inputValue) {
        String fahrenheit = scaleList.get(0);
        String celsius = scaleList.get(1);
        String kelvin = scaleList.get(2);

        if (inputScale.equals(celsius)) {
            if (outputScale.equals(fahrenheit)) {
                return 1.8 * inputValue + 32;
            }

            if (outputScale.equals(kelvin)) {
                return inputValue + 273.15;
            }
        }

        if (inputScale.equals(fahrenheit)) {
            if (outputScale.equals(celsius)) {
                return 5 * (inputValue - 32) / 9;
            }

            if (outputScale.equals(kelvin)) {
                return (inputValue + 459.67) / 1.8;
            }
        }

        if (inputScale.equals(kelvin)) {
            if (outputScale.equals(celsius)) {
                return inputValue - 273.15;
            }

            if (outputScale.equals(fahrenheit)) {
                return inputValue * 1.8 - 459.67;
            }
        }

        return inputValue;
    }
}