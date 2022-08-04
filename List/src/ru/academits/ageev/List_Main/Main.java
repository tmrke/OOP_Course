package ru.academits.ageev.List_Main;

import ru.academits.ageev.List.ListItem;
import ru.academits.ageev.List.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        ListItem<String> stringListItem1 = new ListItem<>("Первый элемент");
        ListItem<String> stringListItem2 = new ListItem<>("Второй элемент");
        ListItem<String> stringListItem3 = new ListItem<>("Третий элемент");
        ListItem<String> stringListItem4 = new ListItem<>("Четвертый элемент");
        ListItem<String> stringListItem5 = new ListItem<>("Пятый элемент");

        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addFirstElement(stringListItem1);
        list.addFirstElement(stringListItem2);
        list.addFirstElement(stringListItem3);
        list.addFirstElement(stringListItem4);

        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.getElementDataByIndex(i));
        }

        System.out.println("===========================================");

        System.out.println("Первый элемент листа: " + list.getFirstElementData());
        System.out.println("Новое значение элемента по индексу 2: "
                + list.setElementDataByIndex(2, "Новое значение второго элемента"));
        list.setElementByIndex(3, stringListItem5);
        System.out.println("Был ли удален элемент со значением \"Новое значение второго элемента\"? Ответ: "
                + list.hasDeleteByData("Новое значение второго элемента"));
        System.out.println("Удален первый элемент со значением: " + list.deleteFirstElement());

        System.out.println("===========================================");
        System.out.println("Развернутый список:");

        list.reverse();

        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.getElementDataByIndex(i));
        }

        SinglyLinkedList<String> newList = list.copyOf();

        System.out.println("===========================================");
        System.out.println("Новый копированный список:");

        for (int i = 0; i < newList.getSize(); i++) {
            System.out.println(newList.getElementDataByIndex(i));
        }
    }
}