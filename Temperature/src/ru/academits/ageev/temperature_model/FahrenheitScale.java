package ru.academits.ageev.temperature_model;

public class FahrenheitScale implements Scale {
    @Override
    public double convertToCelsius(double temperature) {
        return 5 * (temperature - 32) / 9;
    }

    @Override
    public double convertFromCelsius(double temperature) {
        return 1.8 * temperature + 32;
    }

    @Override
    public String toString() {
        return "Fahrenheit scale";
    }
}