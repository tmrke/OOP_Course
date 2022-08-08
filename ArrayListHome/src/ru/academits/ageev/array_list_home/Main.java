package ru.academits.ageev.array_list_home;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String path = "ArrayListHome/src/ru/academits/ageev/Strings.txt";
        ArrayList<String> fileLinesList = getFileLinesList(path);
        System.out.println("Строки из файла: " + fileLinesList);

        ArrayList<Integer> numbersList = new ArrayList<>(Arrays.asList(7, 7, 1, 1, 2, 4, 3, 4, 3, 5, 6, 7, 8, 2, 4, 7));
        ArrayList<Integer> uniqueNumbersList = getUniqueElementsList(numbersList);
        System.out.println("Новый список без повторяющихся элементов : " + uniqueNumbersList);

        deleteEvenNumbers(numbersList);
        System.out.println("Список \"numberList\" без нечетных чисел: " + numbersList);
    }

    public static ArrayList<String> getFileLinesList(String path) {
        ArrayList<String> fileLinesList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(path))) {
            while (scanner.hasNextLine()) {
                fileLinesList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return fileLinesList;
    }

    public static void deleteEvenNumbers(ArrayList<Integer> numbersList) {
        for (int i = 0; i < numbersList.size(); i++) {
            if (numbersList.get(i) % 2 == 0) {
                numbersList.remove(i);
                i--;
            }
        }
    }

    public static ArrayList<Integer> getUniqueElementsList(ArrayList<Integer> numbersList) {
        ArrayList<Integer> uniqueNumbersList = new ArrayList<>(numbersList.size());

        for (Integer integer : numbersList) {
            if (!uniqueNumbersList.contains(integer)) {
                uniqueNumbersList.add(integer);
            }
        }

        return uniqueNumbersList;
    }
}