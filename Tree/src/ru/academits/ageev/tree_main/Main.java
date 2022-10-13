package ru.academits.ageev.tree_main;

import ru.academits.ageev.tree.Tree;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        Consumer<Integer> consumer = System.out::println;

        Tree<Integer> tree = new Tree<>(null);
        tree.add(9);
        tree.add(6);
        tree.add(17);
        tree.add(3);
        tree.add(8);
        tree.add(16);
        tree.add(20);
        tree.add(1);
        tree.add(4);
        tree.add(7);
        tree.add(12);
        tree.add(19);
        tree.add(21);
        tree.add(2);
        tree.add(5);
        tree.add(11);
        tree.add(14);
        tree.add(18);
        tree.add(10);
        tree.add(13);
        tree.add(15);

        System.out.println("Размер дерева = " + tree.getSize());
        System.out.println("Есть ли в дереве узел со значением \"8\"? Ответ: " + tree.contains(8));
        System.out.println("Был ли удален узел дерева со значением \"2\"? Ответ: " + tree.remove(2));

        System.out.println("Обход дерева в ширину:");
        tree.traverseInWidth(consumer);
        System.out.println("===============================================================================");

        System.out.println("Обход дерева в глубину без рекурсии:");
        tree.traverseInDepth(consumer);
        System.out.println("===============================================================================");

        System.out.println("Обход дерева в глубину c рекурсией:");
        tree.traverseInDepthRecursively(consumer);
    }
}