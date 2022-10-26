package ru.academits.ageev.minesweeper_model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameRecordsReader {
    private String[] fromFileResult;
    private final int positionsCount = 10;

    public String[] getGameRecords() {
        String[] forGameRecordsLabelResult = new String[positionsCount];
        String[] fromFileResult = getFromFileResult();

        for (int i = 0; i < forGameRecordsLabelResult.length; i++) {
            if (getFromFileResult()[i] == null) {
                forGameRecordsLabelResult[i] = i + 1 + " position:   -";
            } else {
                forGameRecordsLabelResult[i] = i + 1 + " position: " + fromFileResult[i];
            }
        }

        return forGameRecordsLabelResult;
    }

    private String[] getFromFileResult() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader("Minesweeper/src/ru/academits/ageev/minesweeper_resources/result.txt"))) {
            fromFileResult = new String[positionsCount];

            for (int i = 0; i < fromFileResult.length; i++) {
                fromFileResult[i] = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fromFileResult;
    }
}
