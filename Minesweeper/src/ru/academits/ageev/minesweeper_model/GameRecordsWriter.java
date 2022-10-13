package ru.academits.ageev.minesweeper_model;

import java.io.*;

public class GameRecordsWriter {
    private final String path = "Minesweeper/src/ru/academits/ageev/minesweeper_resources/result.txt";
    private final String newResult;

    private final int rowsCount = 10;

    private final String[] resultsStrings = new String[rowsCount];

    private final BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
    private final StringBuilder inputBuffer = new StringBuilder();

    public GameRecordsWriter(String newResult) throws FileNotFoundException {
        this.newResult = newResult;
    }

    public void addResult() throws IOException {
        String line;
        String temp;
        boolean flag = true;

        int i = 0;

        while (i < rowsCount) {
            if ((line = bufferedReader.readLine()) != null) {
                if (newResult.compareTo(line) < 0 && flag) {
                    temp = line;
                    line = newResult;
                    inputBuffer.append(line).append(System.lineSeparator());

                    resultsStrings[i] = line;
                    i++;

                    if (i > 9) {
                        break;
                    }

                    flag = false;
                    line = temp;
                }

                inputBuffer.append(line).append(System.lineSeparator());

                resultsStrings[i] = line;
            } else if (resultsStrings[i] == null && flag) {
                resultsStrings[i] = newResult;
                inputBuffer.append(newResult).append(System.lineSeparator());

                break;
            }

            i++;
        }

        bufferedReader.close();

        String inputString = inputBuffer.toString();

        FileOutputStream fileOut = new FileOutputStream(path);
        fileOut.write(inputString.getBytes());
        fileOut.close();
    }
}
