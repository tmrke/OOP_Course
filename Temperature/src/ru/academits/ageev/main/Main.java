package ru.academits.ageev.main;

import ru.academits.ageev.controller.Controller;
import ru.academits.ageev.model.Model;
import ru.academits.ageev.model.ModelInterface;
import ru.academits.ageev.view.GuiView;
import ru.academits.ageev.view.View;

public class Main {
    public static void main(String[] args) {
        ModelInterface model = new Model();
        View guiView = new GuiView();

        Controller controller = new Controller(model, guiView);
        controller.start();
    }
}