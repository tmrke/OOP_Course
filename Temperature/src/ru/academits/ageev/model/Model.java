package ru.academits.ageev.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model implements ModelInterface {
    private final List<Scale> scalesList;

    public Model() {
        scalesList = new ArrayList<>(Arrays.asList(
                new Celsius(),
                new Fahrenheit(),
                new Kelvin())
        );
    }

    @Override
    public List<Scale> getScalesList() {
        return scalesList;
    }

    @Override
    public double getOutputValue(Scale inputScale, Scale outputScale, double inputValue) {
        double currentValue = inputScale.convertToCelsius(inputValue);

        return outputScale.convertFromCelsius(currentValue);
    }
}