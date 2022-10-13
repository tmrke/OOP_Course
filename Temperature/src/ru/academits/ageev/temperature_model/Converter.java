package ru.academits.ageev.temperature_model;

public record Converter(Scale[] scales) implements Model {
    @Override
    public double getConvertedTemperature(Scale inputScale, Scale outputScale, double inputTemperature) {
        double temperatureInCelsius = inputScale.convertToCelsius(inputTemperature);

        return outputScale.convertFromCelsius(temperatureInCelsius);
    }
}