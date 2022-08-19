package ru.academits.ageev.hash_table_main;

import ru.academits.ageev.hash_table.HashTable;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        HashTable<String> hashTable = new HashTable<>(10);

        hashTable.add("1");
        hashTable.add("2");
        hashTable.add("3");
        hashTable.add("4");
        hashTable.add("5");
        hashTable.add("6");
        hashTable.add("994");

        System.out.println(hashTable);
        System.out.println("Содержит ли хэш-таблица \"994\"? Ответ: " + hashTable.contains("994"));

        System.out.println("Хэш-таблица как массив: " + Arrays.toString(hashTable.toArray()));

        System.out.println("Был ли удален элемент \"994\"? Ответ: " + hashTable.remove("994"));

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("01", "02", "03"));

        System.out.println("Был ли добавлен \"arrayList\" в хэш-таблицу? Ответ: " + hashTable.addAll(arrayList));

        hashTable.clear();
        System.out.println(hashTable);

        for (String s : hashTable) {
            System.out.println(s);
        }

    }
}
