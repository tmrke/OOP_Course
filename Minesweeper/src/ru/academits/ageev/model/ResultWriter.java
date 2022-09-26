package ru.academits.ageev.model;

import java.io.*;
import java.util.Arrays;

public class ResultWriter {
    private final String path = "Minesweeper/src/ru/academits/ageev/resources/result.txt";
    private final String newResult;

    private final String[] resultsStrings = new String[10];

    private final BufferedReader file = new BufferedReader(new FileReader(path));
    private final StringBuilder inputBuffer = new StringBuilder();

    public ResultWriter(String newResult) throws FileNotFoundException {
        this.newResult = newResult;
    }

    public void addResult() throws IOException {
        String line;
        String temp;
        boolean flag = true;

        int i = 0;

        while (i < 10) {
            if ((line = file.readLine()) != null) {
                if (newResult.compareTo(line) < 0 && flag) {
                    temp = line;
                    line = newResult;
                    inputBuffer.append(line);
                    inputBuffer.append('\n');

                    resultsStrings[i] = line;
                    i++;

                    if (i > 9) {
                        break;
                    }

                    flag = false;
                    line = temp;
                }

                inputBuffer.append(line);
                inputBuffer.append('\n');

                resultsStrings[i] = line;
            } else if (resultsStrings[i] == null && flag) {
                resultsStrings[i] = newResult;
                inputBuffer.append(newResult);
                inputBuffer.append('\n');
                break;
            }

            i++;
        }

        System.out.println(Arrays.toString(resultsStrings));

        file.close();

        String inputString = inputBuffer.toString();

        FileOutputStream fileOut = new FileOutputStream(path);
        fileOut.write(inputString.getBytes());
        fileOut.close();
    }
}
