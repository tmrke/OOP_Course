package ru.academits.ageev.view;

import ru.academits.ageev.model.Cell;
import ru.academits.ageev.model.ModelInterface;
import ru.academits.ageev.model.GameRecordsWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class GuiView implements View {
    private final HashMap<JButton, Cell> cellJButtonHashMap;
    private final Menu menu = new Menu();
    private JPanel menuPanel;
    private JPanel field;
    private JFrame frame;

    private final ModelInterface model;

    public GuiView(ModelInterface model) {
        this.model = model;
        field = new JPanel();
        cellJButtonHashMap = new LinkedHashMap<>();
    }

    @Override
    public void start(ArrayList<ActionListener> actionListenerList) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Minesweeper");
            frame.setSize(450, 470);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(450, 450));

            menuPanel = menu.getMenuPanel(model.getSizesString(), model.getFlagsCount());
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
            JButton cellButton = new JButton();
            field.add(cellButton);
            cellJButtonHashMap.put(cellButton, cell);
        }

        field.setVisible(true);

        return field;
    }

    @Override
    public String getSelectedSizeString() {
        return getMenu().getSelectedSizeString();
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
            frame.setSize(1240, 720);
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
        menu.getAbout().addActionListener(e -> SwingUtilities.invokeLater(() -> {
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

    @Override
    public void clickToCage() {
        for (JButton cellButton : cellJButtonHashMap.keySet()) {
            cellButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {              //leftMouseClick
                        if (!cellButton.isEnabled()) {
                            return;
                        }

                        Cell cell = cellJButtonHashMap.get(cellButton);

                        if (cell.isBomb()) {
                            cellButton.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/resources/bang.png"));
                            JOptionPane.showMessageDialog(field, "Game over");
                        } else {
                            cellButton.setEnabled(false);
                            model.openWithoutBombZone(cell);

                            for (JButton cellButton : cellJButtonHashMap.keySet()) {
                                Cell cell1 = cellJButtonHashMap.get(cellButton);
                                int aroundBombsCount = cell1.getAroundBombsCount();

                                if (aroundBombsCount != 0) {
                                    cellButton.setText(String.valueOf(aroundBombsCount));
                                }

                                if (cell1.isOpen()) {
                                    cellButton.setEnabled(false);
                                }
                            }
                        }
                    } else if (e.getButton() == MouseEvent.BUTTON3) {           //rightMouseClick
                        Cell currentCell = cellJButtonHashMap.get(cellButton);

                        if (!currentCell.isMarkedBomb()) {
                            cellButton.setIcon(new ImageIcon("Minesweeper/src/ru/academits/ageev/resources/flag.png"));
                            currentCell.setMarkedBomb(true);
                            model.setFlagsCount(model.getFlagsCount() - 1);

                            if (currentCell.isBomb()) {
                                model.setMarkedBombsCount(model.getMarkedBombsCount() + 1);
                            }

                            if (model.winGame()) {
                                String resultString = menu.getTime();
                                GameRecordsWriter gameRecordsWriter;

                                try {
                                    gameRecordsWriter = new GameRecordsWriter(resultString);
                                } catch (FileNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }

                                try {
                                    gameRecordsWriter.addResult();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }

                                JOptionPane.showMessageDialog(menuPanel, "You win! Your result: " + resultString);
                            }
                        } else {
                            cellButton.setIcon(null);
                            currentCell.setMarkedBomb(false);
                            model.setFlagsCount(model.getFlagsCount() + 1);

                            if (currentCell.isBomb()) {
                                model.setMarkedBombsCount(model.getMarkedBombsCount() - 1);
                            }
                        }

                        menu.setFlagsCountLabel(model.getFlagsCount());
                    }
                }
            });
        }
    }
}