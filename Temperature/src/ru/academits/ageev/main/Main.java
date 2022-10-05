package ru.academits.ageev.main;

import ru.academits.ageev.controller.Controller;
import ru.academits.ageev.model.*;
import ru.academits.ageev.view.GuiView;
import ru.academits.ageev.view.View;

public class Main {
    public static void main(String[] args) {
        Scale[] scales = new Scale[]{
                new CelsiusScale(),
                new FahrenheitScale(),
                new KelvinScale()
        };

        Model converter = new Converter(scales);
        View guiView = new GuiView();

        Controller controller = new Controller(converter, guiView);
        controller.start();
    }
}