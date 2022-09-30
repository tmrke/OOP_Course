package ru.academits.ageev.model;

import ru.academits.ageev.view.Cage;
import ru.academits.ageev.view.Menu;
import ru.academits.ageev.view.View;

import java.io.IOException;
import java.util.ArrayList;

public interface ModelInterface {

    ArrayList<Cage> getCageList();

    ArrayList<Cage> getNewCageList(String sizeString);

    int getFlagCount();

    String[] getSizesString();

    Integer[] getSizeBySizeString(String sizeString);

    void leftMouseClick(Cage cage, View view);

    void rightMouseClick(Cage cage, Menu menu) throws IOException;
}
