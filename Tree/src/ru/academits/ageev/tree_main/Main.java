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

        System.out.println(tree.removeByData(7));
        System.out.println(tree.root.getRight().getLeft().getData());
    }
}
