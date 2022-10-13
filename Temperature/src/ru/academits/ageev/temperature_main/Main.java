package ru.academits.ageev.temperature_main;

import ru.academits.ageev.temperature_model.*;
import ru.academits.ageev.temperature_view.GuiView;
import ru.academits.ageev.temperature_view.View;

public class Main {
    public static void main(String[] args) {
        Scale[] scales = new Scale[]{
                new CelsiusScale(),
                new FahrenheitScale(),
                new KelvinScale()
        };

        Model converter = new Converter(scales);
        View guiView = new GuiView();

        guiView.start(converter, e -> {
            try {
                guiView.setOutputTemperature(converter.getConvertedTemperature(
                        guiView.getInputScale(),
                        guiView.getOutputScale(),
                        guiView.getInputTemperature()
                ));
            } catch (NumberFormatException ex) {
                guiView.showErrorMessage();
            }
        });
    }
}