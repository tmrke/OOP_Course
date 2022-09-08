package ru.academits.ageev.tree_main;

import ru.academits.ageev.tree.Tree;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();
        tree.add(7);
        tree.add(8);
        tree.add(9);
        tree.add(2);
        tree.add(1);
        tree.add(3);
        tree.add(4);
        tree.add(6);
        tree.add(8);

        System.out.println("Размер дерева = " + tree.getSize());
        System.out.println("Получаем данные узла дерева со значением \"8\": " + tree.getByData(8).getData());
        System.out.println("Был ли удален узел дерева со значением \"2\"? Ответ: " + tree.removeByData(2));

        System.out.println("Обход дерева в ширину:");
        tree.widthVisit();
        System.out.println("===============================================================================");

        System.out.println("Обход дерева в глубину без рекурсии:");
        tree.deepVisit();
        System.out.println("===============================================================================");

        System.out.println("Обход дерева в глубину c рекурсией:");
        tree.deepVisitRecursively();
    }
}
