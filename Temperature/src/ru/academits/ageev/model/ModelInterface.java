package ru.academits.ageev.model;

import java.util.List;

public interface ModelInterface {
    List<Scale> getScalesList();

    double getOutputValue(Scale inputScale, Scale outputScale, double inputValue);
}
