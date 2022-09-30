package ru.academits.ageev.view;

import ru.academits.ageev.model.ModelInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GuiView implements View {
    private final Menu menu;
    private Field field;
    private final JFrame frame;

    public GuiView(ModelInterface model) {
        SwingUtilities.invokeLater(() -> {
            //все сюда
        });

        frame = new JFrame("Minesweeper");
        frame.setSize(450, 470);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(450, 450));

        menu = new Menu(model.getSizesString(), model.getFlagCount());
        frame.add(menu, BorderLayout.NORTH);

        String selectedItem = (String) menu.getFieldSizeComboBox().getSelectedItem();

        field = new Field(model.getSizeBySizeString(selectedItem), model.getNewCageList(selectedItem));
        frame.add(field, BorderLayout.CENTER);
    }

    @Override
    public Menu getMenu() {
        return menu;
    }

    @Override
    public void setField(Integer[] size, ArrayList<Cage> cageList) {
        frame.remove(field);
        field = new Field(size, cageList);
        frame.add(field);
    }

    @Override
    public void setSizeFrame(String sizeFrame) {
        if (sizeFrame.equals("16 x 30")) {
            frame.setSize(1200, 700);
        } else if (sizeFrame.equals("16 x 16")) {
            frame.setSize(680, 700);
        } else {
            frame.setSize(450, 470);
        }
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public void clickExit(ActionListener actionListener) {
        getMenu().getExitButton().addActionListener(actionListener);
    }
}

