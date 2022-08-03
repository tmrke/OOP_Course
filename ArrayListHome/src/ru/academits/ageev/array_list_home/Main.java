package ru.academits.ageev.array_list_home;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> stringsList = getFileStrings();
        System.out.println("Строки из файла: " + stringsList);

        ArrayList<Integer> numbersList = new ArrayList<>(Arrays.asList(7, 7, 1, 1, 2, 4, 3, 4, 3, 5, 6, 7, 8, 2, 4, 7));
        ArrayList<Integer> newNumbersList = getUniqueElementsList(numbersList);
        System.out.println("Новый список без повторяющихся элементов : " + newNumbersList);

        deleteEvenNumbers(numbersList);
        System.out.println("Сипсок \"numberList\" без нечетных чисел: " + numbersList);
    }

    public static ArrayList<String> getFileStrings() {
        ArrayList<String> stringsList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream("ArrayListHome/src/ru/academits/ageev/Strings.txt"))) {
            while (scanner.hasNextLine()) {
                stringsList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return stringsList;
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