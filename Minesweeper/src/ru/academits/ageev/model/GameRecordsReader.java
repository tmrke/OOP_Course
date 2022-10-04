package ru.academits.ageev.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameRecordsReader {
    private String[] fromFileResult;

    public String[] getGameRecords() {
        String[] forGameRecordsLabelResult = new String[10];

        for (int i = 0; i < forGameRecordsLabelResult.length; i++) {
            if (getFromFileResult()[i] == null) {
                forGameRecordsLabelResult[i] = i + 1 + " position:   -";
            } else {
                forGameRecordsLabelResult[i] = i + 1 + " position: " + getFromFileResult()[i];
            }
        }

        return forGameRecordsLabelResult;
    }

    private String[] getFromFileResult() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader("Minesweeper/src/ru/academits/ageev/resources/result.txt"))) {
            fromFileResult = new String[10];

            for (int i = 0; i < fromFileResult.length; i++) {
                fromFileResult[i] = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fromFileResult;
    }
}
