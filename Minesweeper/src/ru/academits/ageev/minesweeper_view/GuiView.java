package ru.academits.ageev.minesweeper_view;

import ru.academits.ageev.minesweeper_model.Cell;
import ru.academits.ageev.minesweeper_model.GameRecordsReader;
import ru.academits.ageev.minesweeper_model.GameRecordsWriter;
import ru.academits.ageev.minesweeper_model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class GuiView implements View {
    private final HashMap<JButton, Cell> cellJButtonHashMap;
    private final Menu menu;
    private JPanel menuPanel;
    private JPanel field;
    private JFrame frame;

    private final Model model;

    private final ImageIcon bangIcon = new ImageIcon("Minesweeper/src/ru/academits/ageev/minesweeper_resources/bang.png");
    private final ImageIcon flagIcon = new ImageIcon("Minesweeper/src/ru/academits/ageev/minesweeper_resources/flag.png");
    private int currentDigital;
    private ImageIcon digitalIcon = new ImageIcon("Minesweeper/src/ru/academits/ageev/minesweeper_resources/" + currentDigital + ".png");

    public GuiView(Model model) {
        this.model = model;
        menu = new Menu(model);
        cellJButtonHashMap = new LinkedHashMap<>();
    }

    @Override
    public void start(List<ActionListener> actionListenerList) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Minesweeper");
            frame.setSize(450, 470);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(450, 450));
            frame.setLocationRelativeTo(null);

            field = new JPanel();

            menuPanel = menu.getMenuPanel(model.getSizesString(), model.getFlagsCount());
            frame.add(menuPanel, BorderLayout.NORTH);

            String selectedItem = (String) menu.getFieldSizeComboBox().getSelectedItem();

            field = getFieldPanel(model.getSizeBySizeString(selectedItem), model.getNewCellList(selectedItem));
            frame.add(field, BorderLayout.CENTER);

            clickNewGame(actionListenerList.get(0));
            clickToAbout();
            clickHighScore(actionListenerList.get(1));
            clickExit(actionListenerList.get(2));

            clickToCell();
        });
    }

    @Override
    public void setTime(String timeString) {
        getMenu().getTimeResult().setText(timeString);
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
        if (sizeFrame.equals(model.getSizeStringBySize(model.getAllSizes()[2]))) {
            frame.setSize(1240, 720);
        } else if (sizeFrame.equals(model.getSizeStringBySize(model.getAllSizes()[1]))) {
            frame.setSize(680, 700);
        } else if (sizeFrame.equals(model.getSizeStringBySize(model.getAllSizes()[0]))) {
            frame.setSize(450, 470);
        }
    }

    @Override
    public void setFlagsCount(int bombsCount) {
        getMenu().setFlagsCountLabel(model.getBombsCount());
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
            JFrame frameAbout = new JFrame("About game");
            frameAbout.setSize(400, 150);
            frameAbout.setVisible(true);
            frameAbout.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));

            JTextArea aboutTextArea = new JTextArea();
            StringBuilder stringBuilder = new StringBuilder();

            try {
                Scanner scanner = new Scanner(new FileInputStream("Minesweeper/src/ru/academits/ageev/minesweeper_resources/about.txt"));
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
    public void clickToCell() {
        if (model.winGame()) {
            return;
        }

        for (JButton cellButton : cellJButtonHashMap.keySet()) {
            cellButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {              //leftMouseClick
                        leftMouseClickToCell(e, cellButton);
                    } else if (e.getButton() == MouseEvent.BUTTON3) {           //rightMouseClick
                        rightMouseClickToCell(cellButton);
                    } else if (e.getButton() == MouseEvent.BUTTON2) {         // wheelMouseClick
                        wheelMouseClickToCell(cellButton);
                    }
                }
            });
        }
    }

    private void leftMouseClickToCell(MouseEvent e, JButton cellButton) {
        if (e.getButton() == MouseEvent.BUTTON1)
            if (!cellButton.isEnabled()) {
                return;
            }

        Cell cell = cellJButtonHashMap.get(cellButton);

        if (cell.isMarked()) {
            return;
        }

        if (cell.isBomb()) {
            for (JButton currentCellButton : cellJButtonHashMap.keySet()) {
                if (cellJButtonHashMap.get(currentCellButton).isBomb()) {
                    currentCellButton.setIcon(bangIcon);
                    model.setMarkedBombsCount(0);
                }
            }

            JOptionPane.showMessageDialog(field, "Game over");
        } else {
            cellButton.setEnabled(false);
            int around3x3BombCount = model.getAround3x3BombCount(cell);

            if (around3x3BombCount > 0) {
                cellButton.setText(String.valueOf(around3x3BombCount));

                return;
            }

//            if (around3x3BombCount > 0) {
//                setDigitalIcon(cellButton, around3x3BombCount);
//
//                return;
//            }

            model.openWithoutBombCells(cell);

            for (JButton currentCellButton : cellJButtonHashMap.keySet()) {
                Cell currentCell = cellJButtonHashMap.get(currentCellButton);
                int aroundBombsCount = currentCell.getAroundBombsCount();

                if (currentCell.isMarked()) {
                    continue;
                }

                if (aroundBombsCount != 0) {
                    currentCellButton.setText(String.valueOf(aroundBombsCount));
                }

                if (currentCell.isOpen()) {
                    currentCellButton.setEnabled(false);
                }
            }
        }
    }

    private void rightMouseClickToCell(JButton cellButton) {
        Cell currentCell = cellJButtonHashMap.get(cellButton);

        if (!cellButton.isEnabled()) {
            return;
        }

        if (model.getFlagsCount() == 0) {
            if (currentCell.isMarked()) {
                cellButton.setIcon(null);
                currentCell.setMarked(false);
                model.setFlagsCount(model.getFlagsCount() + 1);

                if (currentCell.isBomb()) {
                    model.setMarkedBombsCount(model.getMarkedBombsCount() - 1);
                }
            }

            return;
        }

        if (!currentCell.isMarked()) {
            cellButton.setIcon(flagIcon);
            currentCell.setMarked(true);
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

                model.stopTimer();
                model.setMarkedBombsCount(0);

                JOptionPane.showMessageDialog(menuPanel, "You win! Your result: " + resultString);
            }
        } else {
            cellButton.setIcon(null);
            currentCell.setMarked(false);
            model.setFlagsCount(model.getFlagsCount() + 1);

            if (currentCell.isBomb()) {
                model.setMarkedBombsCount(model.getMarkedBombsCount() - 1);
            }
        }

        menu.setFlagsCountLabel(model.getFlagsCount());
    }

    private void wheelMouseClickToCell(JButton cellButton) {
        Cell currentCell = cellJButtonHashMap.get(cellButton);
        int bombAroundCount = model.getAround3x3BombCount(currentCell);

        if (!cellButton.isEnabled() && bombAroundCount > 0 && bombAroundCount == model.getAround3x3FlagCount(currentCell)) {
            if (model.is3x3AreaClear(currentCell)) {
                model.scanField3x3CellList(currentCell);

                for (JButton currentCellButton : cellJButtonHashMap.keySet()) {
                    Cell currentAround3x3Cell = cellJButtonHashMap.get(currentCellButton);

                    if (currentAround3x3Cell.isMarked()) {
                        continue;
                    }

                    if (currentAround3x3Cell.isOpen()) {
                        currentCellButton.setEnabled(false);

                        int around3x3BombCount = model.getAround3x3BombCount(currentAround3x3Cell);

                        if (around3x3BombCount > 0) {
                            currentCellButton.setText(String.valueOf(around3x3BombCount));
                        }
                    }
                }
            } else {
                for (JButton currentCellButton : cellJButtonHashMap.keySet()) {
                    if (cellJButtonHashMap.get(currentCellButton).isBomb()) {
                        currentCellButton.setIcon(bangIcon);
                    }
                }
            }
        }
    }

    @Override
    public void clickOnHighScoreButton() {
        GameRecordsReader gameRecordsReader = new GameRecordsReader();
        String[] results = gameRecordsReader.getGameRecords();

        JFrame gameRecordsListFrame = new JFrame("High score");
        gameRecordsListFrame.setSize(400, 320);

        GridLayout layout = new GridLayout(10, 0, 10, 10);
        JPanel grid = new JPanel();
        grid.setLayout(layout);

        for (String result : results) {
            JLabel label = new JLabel(result);
            grid.add(label);
        }

        gameRecordsListFrame.add(grid);
        gameRecordsListFrame.setSize(250, 400);
        gameRecordsListFrame.setVisible(true);

        getMenu().getHighScoresButton().setVisible(true);
    }

    public void setDigitalIcon(JButton cellButton, int bombCount) {
        if (bombCount > 0) {
            for (int i = 0; i < 9; i++) {
                if (bombCount == i) {
                    currentDigital = i;
                    cellButton.setIcon(digitalIcon);
                }
            }
        }
    }
}