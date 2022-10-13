package ru.academits.ageev.temperature_model;

public interface Scale {
    double convertToCelsius(double temperature);

    double convertFromCelsius(double temperature);
}