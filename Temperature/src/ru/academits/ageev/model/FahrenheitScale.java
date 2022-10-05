package ru.academits.ageev.model;

public class FahrenheitScale implements Scale {
    @Override
    public double convertToCelsius(double value) {
        return 5 * (value - 32) / 9;
    }

    @Override
    public double convertFromCelsius(double value) {
        return 1.8 * value + 32;
    }

    @Override
    public String toString() {
        return "FahrenheitScale";
    }
}
