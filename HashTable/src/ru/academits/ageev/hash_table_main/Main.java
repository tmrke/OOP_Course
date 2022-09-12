package ru.academits.ageev.hash_table_main;

import ru.academits.ageev.hash_table.HashTable;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        HashTable<String> hashTable1 = new HashTable<>(10);

        hashTable1.add("1");
        hashTable1.add("2");
        hashTable1.add("3");
        hashTable1.add("4");
        hashTable1.add("5");
        hashTable1.add("6");
        hashTable1.add("994");

        System.out.println(hashTable1);
        System.out.println("Содержит ли хэш-таблица \"994\"? Ответ: " + hashTable1.contains("994"));

        System.out.println("Хэш-таблица как массив: " + Arrays.toString(hashTable1.toArray()));

        System.out.println("Был ли удален элемент \"994\"? Ответ: " + hashTable1.remove("994"));

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("01", "02", "03"));
        System.out.println("Был ли добавлен \"arrayList\" в хэш-таблицу? Ответ: " + hashTable1.addAll(arrayList));

        HashTable<String> hashTable2 = new HashTable<>(10);
        hashTable2.add("1");
        hashTable2.add("2");
        hashTable2.add("23");
        hashTable2.add("994");

        System.out.println("Были ли оставлены только элементы \"hashTable2\" в \"hashTable1\"? Ответ: " + hashTable1.retainAll(hashTable2));

        hashTable1.clear();
        System.out.println("\"hashTable1\" после удаления " + hashTable1);

        System.out.println("Были ли удалены элементы из \"hashTable2\" в \"hashTable1\"? Ответ: " + hashTable1.removeAll(hashTable2));
        System.out.println(hashTable1);

        System.out.println("Все ли элементы из \"hashTable2\" присутствует в \"hashTable1\"? Ответ: " + hashTable1.containsAll(hashTable2));

        System.out.println("\"hashTable2\" как массив: " + Arrays.toString(hashTable2.toArray(new String[2])));
    }
}