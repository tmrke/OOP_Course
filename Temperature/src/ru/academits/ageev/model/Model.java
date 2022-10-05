package ru.academits.ageev.model;

public interface Model {
    Scale[] scales();

    double getConvertedTemperature(Scale inputScale, Scale outputScale, double inputValue);
}
