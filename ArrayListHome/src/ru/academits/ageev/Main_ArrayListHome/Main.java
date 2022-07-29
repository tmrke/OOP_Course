package ru.academits.ageev.Main_ArrayListHome;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> stringsList = readFileToArrayList();
        System.out.println(stringsList);

        ArrayList<Integer> numbersList = new ArrayList<>(Arrays.asList(7, 7, 1, 1, 2, 4, 3, 4, 3, 5, 6, 7, 8, 2, 4, 7));
        ArrayList<Integer> newNumbersList = deleteDuplicateElements(numbersList);
        System.out.println(newNumbersList);

        deleteEvenTwoNumbers(numbersList);
        System.out.println(numbersList);
    }

    public static ArrayList<String> readFileToArrayList() {
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

    public static void deleteEvenTwoNumbers(ArrayList<Integer> numbersList) {
        for (int i = 0; i < numbersList.size(); i++) {
            if (numbersList.get(i) % 2 == 0) {
                numbersList.remove(i);
                i--;
            }
        }
    }

    public static ArrayList<Integer> deleteDuplicateElements(ArrayList<Integer> numbersList) {
        ArrayList<Integer> newNumberList = new ArrayList<>();

        for(int i = 0; i < numbersList.size(); i++){
            for (Integer integer : numbersList) {
                if (!newNumberList.contains(integer)) {
                    newNumberList.add(integer);
                }
            }
        }

        return newNumberList;
    }
}

