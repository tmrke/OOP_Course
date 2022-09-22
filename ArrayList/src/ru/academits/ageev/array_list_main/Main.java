package ru.academits.ageev.array_list_main;

import ru.academits.ageev.array_list.ArrayList;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> arrayList1 = new ArrayList<>(5);
        arrayList1.add("Первый элемент");
        arrayList1.add("Второй элемент");
        arrayList1.add("Третий элемент");
        arrayList1.add("Четвертый элемент");
        arrayList1.add("Пятый элемент");

        System.out.println(arrayList1);

        List<String> arrayList2 = new ArrayList<>();
        arrayList2.add("Седьмой элемент");
        arrayList2.add("Восьмой элемент");
        arrayList2.add("Девятый элемент");

        System.out.println(arrayList1);
        System.out.println("Элемент по индексу 4: " + arrayList1.get(4));

        System.out.println("=========================================================================================");

        System.out.println("Добавляем элемент с индексом 5");
        arrayList1.add(5, "Шестой элемент");
        System.out.println(arrayList1);

        System.out.println("=========================================================================================");

        System.out.println("К arrayList1 прибавляем arrayList2 начиная с индекса 3");
        arrayList1.addAll(3, arrayList2);
        System.out.println(arrayList1);

        System.out.println("=========================================================================================");

        System.out.println("Удаляем элемент с индексом 5");
        arrayList1.remove(5);
        System.out.println(arrayList1);

        System.out.println("=========================================================================================");

        System.out.println("Был ли удален \"Восьмой элемент\"? Ответ: " + arrayList1.remove("Восьмой элемент"));
        System.out.println(arrayList1);

        System.out.println("=========================================================================================");

        System.out.println("Удаляем все элементы в arrayList2");
        arrayList2.clear();
        System.out.println(arrayList2);

        System.out.println("=========================================================================================");

        List<String> arrayList3 = new ArrayList<>();
        arrayList3.add("Первый элемент");
        arrayList3.add("5 элемент");
        arrayList3.add("Девятый элемент");

        System.out.println("Удаляем все элементы, находящиеся в arrayList3 из arrayList1");
        arrayList1.removeAll(arrayList3);
        System.out.println(arrayList1);

        System.out.println("=========================================================================================");

        List<String> arrayList4 = new ArrayList<>();
        arrayList4.add("Второй элемент");
        arrayList4.add("Пятый элемент");
        arrayList4.add("Седьмой элемент");

        System.out.println("Оставляем все элементы в arrayList1, которые есть в arrayList4");
        arrayList1.retainAll(arrayList4);
        System.out.println(arrayList1);

        System.out.println("=========================================================================================");

        System.out.println("Содержит ли arrayList1 \"Седьмой элемент\"? Ответ: " + arrayList1.contains("Седьмой элемент"));

        System.out.println("=========================================================================================");

        System.out.println("Содержит ли arrayList4 данные arrayList1? Ответ: " + arrayList4.containsAll(arrayList1));

        System.out.println("=========================================================================================");

        List<String> arrayList5 = new ArrayList<>(5);
        arrayList5.add("1 элемент");
        arrayList5.add("2 элемент");
        arrayList5.add("3 элемент");
        arrayList5.add("1 элемент");
        arrayList5.add("5 элемент");

        System.out.println("=========================================================================================");

        System.out.println("Первый индекс вхождения значения \"Первый элемент\" в arrayList5 = " + arrayList5.indexOf("Первый элемент"));
        System.out.println("Последний индекс вхождения значения \"Первый элемент\" в arrayList5 = " + arrayList5.lastIndexOf("Первый элемент"));

        System.out.println("=========================================================================================");

        System.out.println("arrayList5 как массив: " + Arrays.toString(arrayList5.toArray()));
        System.out.println("arrayList5 как массив: " + Arrays.toString(arrayList5.toArray(new String[10])));

        List<String> arrayList6 = new ArrayList<>(3);
        arrayList6.add("1 элемент");
        arrayList6.add("2 элемент");
        arrayList6.add("3 элемент");
        arrayList6.add("1 элемент");
        arrayList6.add("5 элемент");

        System.out.println("Эквивалетны ли \"arrayList5\" и \"arrayList6?\" Ответ: " + arrayList5.equals(arrayList6));
    }
}