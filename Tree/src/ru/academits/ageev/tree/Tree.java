package ru.academits.ageev.tree;

import java.util.*;
import java.util.function.Consumer;

public class Tree<T> {
    private Node<T> root;
    private int size;
    private final Comparator<? super T> comparator;

    public Tree(Comparator<T> comparator) {
        this.comparator = Objects.requireNonNullElseGet(comparator, () -> this::compare);
    }

    public Tree() {
        comparator = this::compare;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int compare(T t1, T t2) {
        if (t1 == null && t2 == null) {
            return 0;
        }

        if (t1 == null) {
            return -1;
        }

        if (t2 == null) {
            return 1;
        }

        //noinspection unchecked
        return ((Comparable<T>) t1).compareTo(t2);
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
            if (comparator.compare(data, currentNode.getData()) >= 0) {
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
            int comparatorResult = comparator.compare(currentNode.getData(), data);

            if (comparatorResult == 0) {
                return true;
            }

            if (comparatorResult > 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
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

        int comparatorResult = comparator.compare(data, removedNode.getData());
        boolean isLeftChild = false;

        while (comparatorResult != 0) {
            removedNodeParent = removedNode;

            if (comparatorResult < 0) {
                removedNode = removedNode.getLeft();
                isLeftChild = true;
            } else {
                removedNode = removedNode.getRight();
                isLeftChild = false;
            }

            if (removedNode == null) {
                return false;
            }

            comparatorResult = comparator.compare(data, removedNode.getData());
        }

        if (removedNode.getLeft() == null && removedNode.getRight() == null) {
            if (removedNode == root) {
                root = null;
                size--;

                return true;
            }

            if (isLeftChild) {
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

            if (isLeftChild) {
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

            if (isLeftChild) {
                removedNodeParent.setLeft(removedNode.getLeft());
            } else {
                removedNodeParent.setRight(removedNode.getLeft());
            }

            size--;

            return true;
        }

        Node<T> smallestLeftNodeParent = null;
        Node<T> smallestLeftNode = removedNode.getRight();

        Node<T> removedNodeLeftChild = removedNode.getLeft();
        Node<T> removedNodeRightChild = removedNode.getRight();

        if (smallestLeftNode.getLeft() == null) {
            if (removedNodeParent == null) {
                root = smallestLeftNode;
                root.setLeft(removedNodeLeftChild);

                size--;

                return true;
            }

            if (isLeftChild) {
                removedNodeParent.setLeft(removedNodeRightChild);
            } else {
                removedNodeParent.setRight(removedNodeRightChild);
            }

            removedNodeRightChild.setLeft(removedNodeLeftChild);
            size--;

            return true;
        }

        while (smallestLeftNode.getLeft() != null) {
            smallestLeftNodeParent = smallestLeftNode;
            smallestLeftNode = smallestLeftNode.getLeft();
        }

        smallestLeftNodeParent.setLeft(smallestLeftNode.getRight());
        smallestLeftNode.setRight(removedNodeRightChild);
        smallestLeftNode.setLeft(removedNodeLeftChild);

        if (removedNodeParent == null) {
            root = smallestLeftNode;
        } else {
            if (isLeftChild) {
                removedNodeParent.setLeft(smallestLeftNode);
            } else {
                removedNodeParent.setRight(smallestLeftNode);
            }
        }

        size--;

        return true;
    }

    public void traverseInWidth(Consumer<T> consumer) {
        if (isEmpty()) {
            return;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> currentNode = queue.poll();
            consumer.accept(currentNode.getData());

            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }
    }

    public void traverseInDepth(Consumer<T> consumer) {
        if (isEmpty()) {
            return;
        }

        Deque<Node<T>> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node<T> currentNode = stack.pop();
            consumer.accept(currentNode.getData());

            if (currentNode.getRight() != null) {
                stack.push(currentNode.getRight());
            }

            if (currentNode.getLeft() != null) {
                stack.push(currentNode.getLeft());
            }
        }
    }

    public void traverseInDepthRecursively(Consumer<T> consumer) {
        traverseInDepthRecursively(root, consumer);
    }

    private void traverseInDepthRecursively(Node<T> node, Consumer<T> consumer) {
        if (node == null) {
            return;
        }

        consumer.accept(node.getData());

        if (node.getLeft() != null) {
            traverseInDepthRecursively(node.getLeft(), consumer);
        }

        if (node.getRight() != null) {
            traverseInDepthRecursively(node.getRight(), consumer);
        }
    }
}