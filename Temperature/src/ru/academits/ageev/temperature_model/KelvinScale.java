package ru.academits.ageev.temperature_model;

public class KelvinScale implements Scale {
    @Override
    public double convertToCelsius(double value) {
        return value - 273.15;
    }

    @Override
    public double convertFromCelsius(double value) {
        return value + 273.15;
    }

    @Override
    public String toString() {
        return "Kelvin scale";
    }
}