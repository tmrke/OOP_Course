package ru.academits.ageev.list_main;

import ru.academits.ageev.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addFirst("Первый элемент");
        list.addFirst("Второй элемент");
        list.addFirst("Третий элемент");
        list.addFirst("Четвертый элемент");
        list.addFirst("Пятый элемент");

        System.out.println(list);
        System.out.println("Размер списка = " + list.getSize());
        System.out.println("===========================================");

        System.out.println("Первый элемент списка: " + list.getFirst());
        System.out.println("Четвертый элемент списка: " + list.getItemDataByIndex(3));
        System.out.println("Установлено новое значение элемента по индексу 2; Прежнее значение: "
                + list.setItemDataByIndex(2, "Новое значение второго элемента"));
        list.addItemDataByIndex(3, "тоже третий элемент");
        System.out.println("Был ли удален элемент со значением \"Новое значение второго элемента\"? Ответ: "
                + list.deleteByData("Новое значение второго элемента"));
        System.out.println("Удален элемент с индексом 1 со значением: " + list.deleteByIndex(1));
        System.out.println("Удален первый элемент со значением: " + list.deleteFirst());

        System.out.println("===========================================");
        System.out.println("Развернутый список:");

        list.reverse();
        System.out.println(list);

        SinglyLinkedList<String> newList = list.copy();

        System.out.println("===========================================");
        System.out.println("Новый копированный список:");
        System.out.println(newList);
    }
}