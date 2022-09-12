package ru.academits.ageev.view;

public class View {
    private final AppWindow window;

    public View() {
        window = new AppWindow();
    }

    public AppWindow getWindow() {
        return window;
    }
}