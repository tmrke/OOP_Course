package ru.academits.ageev.main;

import ru.academits.ageev.model.Model;
import ru.academits.ageev.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        Model model = new Model(view);

        model.startProgram();
    }
}