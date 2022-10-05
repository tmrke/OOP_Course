package ru.academits.ageev.model;

public record Converter(Scale[] scales) implements Model {
    @Override
    public double getConvertedTemperature(Scale inputScale, Scale outputScale, double inputValue) {
        double currentValue = inputScale.convertToCelsius(inputValue);

        return outputScale.convertFromCelsius(currentValue);
    }
}