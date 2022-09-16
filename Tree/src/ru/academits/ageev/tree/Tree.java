package ru.academits.ageev.tree;

import java.util.*;
import java.util.function.Consumer;

public class Tree<T> {
    private Node<T> root;
    private int size;
    private final Comparator<? super T> comparator;

    public Tree() {
        root = null;
        comparator = null;
        size = 0;
    }

    public Tree(Comparator<T> comparator) {
        root = null;
        this.comparator = comparator;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    private int compare(T t1, T t2) {
        if (comparator != null) {
            return comparator.compare(t1, t2);
        }

        if (t1 == null) {
            if (t2 == null) {
                return 0;
            }

            return -1;
        }

        if (t2 == null) {
            return -1;
        }

        //noinspection unchecked
        return ((Comparable<? super T>) t1).compareTo(t2);
    }

    public void add(T data) {
        Node<T> addedNode = new Node<>(data);

        if (root == null) {
            root = addedNode;
            size++;

            return;
        }

        Node<T> currentNode = root;

        while (true) {
            if (compare(data, currentNode.getData()) >= 0) {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(addedNode);
                    size++;

                    return;
                }

                currentNode = currentNode.getRight();
            } else {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(addedNode);
                    size++;

                    return;
                }

                currentNode = currentNode.getLeft();
            }
        }
    }

    public boolean contains(T data) {
        if (isEmpty()) {
            return false;
        }

        Node<T> currentNode = root;

        while (currentNode != null) {
            if (compare(currentNode.getData(), data) == 0) {
                return true;
            }

            if (compare(data, currentNode.getData()) >= 0) {
                currentNode = currentNode.getRight();
            } else {
                currentNode = currentNode.getLeft();
            }
        }

        return false;
    }

    public boolean remove(T data) {
        if (isEmpty()) {
            return false;
        }

        Node<T> removedNode = root;
        Node<T> removedNodeParent = null;

        int comparatorResult = compare(data, removedNode.getData());
        boolean isLeftChildren = false;

        while (comparatorResult != 0) {
            removedNodeParent = removedNode;

            if (comparatorResult < 0) {
                removedNode = removedNode.getLeft();
                isLeftChildren = true;
            } else {
                removedNode = removedNode.getRight();
                isLeftChildren = false;
            }

            if (removedNode == null) {
                return false;
            }

            comparatorResult = compare(data, removedNode.getData());
        }

        if (removedNode.getLeft() == null && removedNode.getRight() == null) {
            if (removedNode == root) {
                root = null;
                size--;

                return true;
            }

            if (isLeftChildren) {
                removedNodeParent.setLeft(null);
            } else {
                removedNodeParent.setRight(null);
            }

            size--;

            return true;
        }

        if (removedNode.getLeft() == null) {
            if (removedNode == root) {
                root = root.getRight();
                size--;

                return true;
            }

            if (isLeftChildren) {
                removedNodeParent.setLeft(removedNode.getRight());
            } else {
                removedNodeParent.setRight(removedNode.getRight());
            }

            size--;

            return true;
        }

        if (removedNode.getRight() == null) {
            if (removedNode == root) {
                root = root.getLeft();
                size--;

                return true;
            }

            if (isLeftChildren) {
                removedNodeParent.setLeft(removedNode.getLeft());
            } else {
                removedNodeParent.setRight(removedNode.getLeft());
            }

            size--;

            return true;
        }

        Node<T> smallestLeftNodeParent = null;
        Node<T> smallestLeftNode = removedNode.getRight();

        Node<T> leftRemovedNodeChild = removedNode.getLeft();
        Node<T> rightRemovedNodeChild = removedNode.getRight();

        if (smallestLeftNode.getLeft() == null) {
            if (isLeftChildren) {
                removedNodeParent.setLeft(rightRemovedNodeChild);
            } else {
                removedNodeParent.setRight(rightRemovedNodeChild);
            }

            rightRemovedNodeChild.setLeft(leftRemovedNodeChild);
            size--;

            return true;
        }

        while (smallestLeftNode.getLeft() != null) {
            smallestLeftNodeParent = smallestLeftNode;
            smallestLeftNode = smallestLeftNode.getLeft();
        }

        if (smallestLeftNode.getRight() == null) {
            smallestLeftNodeParent.setLeft(null);
        } else {
            smallestLeftNodeParent.setLeft(smallestLeftNode.getRight());
        }

        removedNodeParent.setRight(smallestLeftNode);
        smallestLeftNode.setLeft(leftRemovedNodeChild);
        smallestLeftNode.setRight(rightRemovedNodeChild);
        size--;

        return true;
    }

    public void traverseLevelOrder(Consumer<T> consumer) {
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();
            consumer.accept(current.getData());

            if (current.getLeft() != null) {
                queue.add(current.getLeft());
            }

            if (current.getRight() != null) {
                queue.add(current.getRight());
            }
        }
    }

    public void traverseInOrder(Consumer<T> consumer) {
        Deque<Node<T>> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node<T> current = stack.pop();
            consumer.accept(current.getData());

            if (current.getRight() != null) {
                stack.push(current.getRight());
            }

            if (current.getLeft() != null) {
                stack.push(current.getLeft());
            }
        }
    }

    public void traverseInOrderRecursively(Consumer<T> consumer) {
        traverseInOrderRecursively(root, consumer);
    }

    private void traverseInOrderRecursively(Node<T> node, Consumer<T> consumer) {
        if (node == null) {
            return;
        }

        consumer.accept(node.getData());

        if (node.getLeft() != null) {
            traverseInOrderRecursively(node.getLeft(), consumer);
        }

        if (node.getRight() != null) {
            traverseInOrderRecursively(node.getRight(), consumer);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }
}