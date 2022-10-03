package ru.academits.ageev.view;

import ru.academits.ageev.model.ModelInterface;
import ru.academits.ageev.model.ResultWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GuiView implements View {
    private final Menu menu = new Menu();
    private JPanel menuPanel;
    private JPanel field;
    private JFrame frame;

    private final ModelInterface model;

    public GuiView(ModelInterface model) {
        this.model = model;
        field = new JPanel();
    }

    @Override
    public void start(ArrayList<ActionListener> actionListenerList) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Minesweeper");
            frame.setSize(450, 470);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(450, 450));

            menuPanel = menu.getMenuPanel(model.getSizesString(), model.getFlagCount());
            frame.add(menuPanel, BorderLayout.NORTH);

            String selectedItem = (String) menu.getFieldSizeComboBox().getSelectedItem();

            field = getFieldPanel(model.getSizeBySizeString(selectedItem), model.getNewCageList(selectedItem));
            frame.add(field, BorderLayout.CENTER);

            clickNewGame(actionListenerList.get(0));
            clickToAbout();
            clickHighScore(actionListenerList.get(1));
            clickExit(actionListenerList.get(2));

            clickToCage();
        });
    }

    public JPanel getFieldPanel(Integer[] size, ArrayList<Cell> cellList) {
        field = new JPanel();

        GridLayout layout = new GridLayout(size[0], size[1]);
        field.setLayout(layout);

        for (Cell cell : cellList) {
            field.add(cell);
        }

        field.setVisible(true);

        return field;
    }

    @Override
    public Menu getMenu() {
        return menu;
    }

    @Override
    public void setField(Integer[] size, ArrayList<Cell> cellList) {
        frame.remove(field);
        field = getFieldPanel(size, cellList);
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
    public void clickExit(ActionListener actionListener) {
        menu.getExitButton().addActionListener(actionListener);
    }

    @Override
    public void clickHighScore(ActionListener actionListener) {
        menu.getHighScoresButton().addActionListener(actionListener);
    }

    @Override
    public void clickNewGame(ActionListener actionListener) {
        menu.getNewGameButton().addActionListener(actionListener);
    }

    @Override
    public void clickToAbout() {
        menu.getAbout().addActionListener(e ->
                SwingUtilities.invokeLater(() -> {
                    JFrame frameAbout = new JFrame();
                    frameAbout.setSize(400, 150);
                    frameAbout.setVisible(true);

                    JTextArea aboutTextArea = new JTextArea();
                    StringBuilder stringBuilder = new StringBuilder();

                    try {
                        Scanner scanner = new Scanner(new FileInputStream("Minesweeper/src/ru/academits/ageev/resources/about.txt"));
                        while (scanner.hasNextLine()) {
                            stringBuilder.append(scanner.nextLine()).append("\n");
                        }
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                    aboutTextArea.append(String.valueOf(stringBuilder));
                    aboutTextArea.setSize(300, 200);
                    frameAbout.add(aboutTextArea);
                }));
    }

    public void clickToCage() {
        ArrayList<Cell> cellList = model.getCageList();

        for (Cell cell : cellList) {
            cell.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {          //leftMouseClick
                        if (!cell.isEnabled()) {
                            return;
                        }

                        if (cell.isBomb()) {
                            cell.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/resources/bang.png"));
                            JOptionPane.showMessageDialog(field, "Game over");
                        } else {
                            cell.setEnabled(false);
                            model.openWithoutBombZone(cell);
                        }
                    } else if (e.getButton() == MouseEvent.BUTTON3) {       //rightMouseClick
                        if (!cell.isMarkedBomb()) {
                            cell.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/resources/flag.png"));
                            cell.setMarkedBomb(true);
                            model.setFlagCount(model.getFlagCount() - 1);

                            if (cell.isBomb()) {
                                model.setMarkedBombCount(model.getMarkedBombCount() + 1);
                            }

                            if (model.winGame()) {
                                String resultString = menu.getTime();
                                ResultWriter resultWriter;

                                try {
                                    resultWriter = new ResultWriter(resultString);
                                } catch (FileNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }

                                try {
                                    resultWriter.addResult();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }

                                JOptionPane.showMessageDialog(menuPanel, "You win! Your result: " + resultString);
                            }
                        } else {
                            cell.setIcon(null);
                            cell.setMarkedBomb(false);
                            model.setFlagCount(model.getFlagCount() + 1);

                            if (cell.isBomb()) {
                                model.setMarkedBombCount(model.getMarkedBombCount() - 1);
                            }
                        }

                        menu.setFlagCountLabel(model.getFlagCount());
                    }
                }
            });
        }
    }
}
