package ru.academits.ageev.tree;

import ru.academits.ageev.tree_main.Node;

import java.util.*;

public class Tree<T> {
    public Node<T> root;
    private int size;

    public int getSize() {
        return size;
    }

    private int compare(T t1, T t2) {
        if (t1 == null || t2 == null) {
            throw new NullPointerException("Arguments can't be null, t1 = " + t1 + "; t2 = " + t2);
        }

        //noinspection unchecked
        return ((Comparable<? super T>) t1).compareTo(t2);
    }

    public void add(T data) {
        Node<T> addedNode = new Node<>(data);

        if (root == null) {
            root = addedNode;
            size++;
        } else {
            Node<T> currentNode = root;

            while (true) {
                if (compare(addedNode.getData(), currentNode.getData()) >= 0) {
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
    }

    public Node<T> getByData(T data) {
        checkEmpty();

        Node<T> currentNode = root;

        while (currentNode != null) {
            if (Objects.equals(currentNode.getData(), data)) {
                return currentNode;
            }

            if (compare(data, currentNode.getData()) >= 0) {
                currentNode = currentNode.getRight();
            } else {
                currentNode = currentNode.getLeft();
            }
        }

        return null;
    }


    public boolean removeByData(T data) {
        checkEmpty();

        Node<T> deletedNode = root;
        Node<T> deletedNodeParent = null;

        int comparatorResult = compare(data, deletedNode.getData());
        boolean isLeftChildren = false;

        while (comparatorResult != 0) {
            deletedNodeParent = deletedNode;

            if (comparatorResult < 0) {
                deletedNode = deletedNode.getLeft();
                isLeftChildren = true;
            } else {
                deletedNode = deletedNode.getRight();
            }

            if (deletedNode == null) {
                return false;
            }

            comparatorResult = compare(data, deletedNode.getData());
        }

        if (deletedNode.getLeft() == null && deletedNode.getRight() == null) {
            if (root == deletedNode) {
                root = null;
                size--;

                return true;
            }

            deletedNodeParent.setLeft(null);
            deletedNodeParent.setRight(null);
            size--;

            return true;
        }

        if (deletedNode.getLeft() == null) {
            if (root == deletedNode) {
                root = root.getRight();
                size--;

                return true;
            }

            deletedNodeParent.setRight(deletedNode.getRight());
            size--;

            return true;
        }

        if (deletedNode.getRight() == null) {
            if (root == deletedNode) {
                root = root.getLeft();
                size--;

                return true;
            }

            deletedNodeParent.setLeft(deletedNode.getLeft());
            size--;

            return true;
        }

        Node<T> smallestLeftNode = deletedNode.getRight();
        Node<T> smallestLeftNodeParent = deletedNode;
        boolean isLeftSmallestLeftNode = false;

        while (smallestLeftNode.getLeft() != null) {
            smallestLeftNodeParent = smallestLeftNode;
            smallestLeftNode = smallestLeftNode.getLeft();
            isLeftSmallestLeftNode = true;
        }

        if (root == deletedNode) {
            if (root.getLeft() == null && root.getRight() == null) {
                root = null;
            } else if (root.getRight() != null && root.getLeft() == null) {
                root = root.getRight();
            } else if (root.getLeft() != null && root.getRight() == null) {
                root = root.getLeft();
            } else {
                Node<T> rootLeftNode = root.getLeft();

                if (isLeftSmallestLeftNode) {
                    if (smallestLeftNode.getRight() == null) {
                        root = smallestLeftNode;
                    } else {
                        root = smallestLeftNode.getRight();
                    }
                } else {
                    root = smallestLeftNode;
                }

                root.setLeft(rootLeftNode);

            }

            size--;

            return true;
        }

        if (isLeftSmallestLeftNode) {
            if (smallestLeftNode.getRight() != null) {
                smallestLeftNodeParent.setRight(smallestLeftNode.getRight());
            } else {
                smallestLeftNodeParent.setLeft(null);
            }
        } else {
            if (smallestLeftNode.getRight() != null) {
                smallestLeftNodeParent.setRight(smallestLeftNode.getRight());
            } else {
                smallestLeftNodeParent.setRight(null);
            }
        }

        if (isLeftChildren) {
            deletedNodeParent.setLeft(smallestLeftNode);
            smallestLeftNode.setLeft(deletedNode.getLeft());
        } else {
            deletedNodeParent.setRight(smallestLeftNode);
        }

        size--;

        return true;
    }

    public void widthVisit() {
        checkEmpty();

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        Node<T> queueCurrentNode;

        while (!queue.isEmpty()) {
            queueCurrentNode = queue.poll();
            System.out.println(queueCurrentNode.getData());

            if (queueCurrentNode.getLeft() != null) {
                queue.add(queueCurrentNode.getLeft());
            }

            if (queueCurrentNode.getRight() != null) {
                queue.add(queueCurrentNode.getRight());
            }
        }
    }

    public void deepVisit() {
        checkEmpty();

        Stack<Node<T>> stack = new Stack<>();
        stack.push(root);

        Node<T> stackCurrentNode;

        while (!stack.isEmpty()) {
            stackCurrentNode = stack.pop();
            System.out.println(stackCurrentNode.getData());

            if (stackCurrentNode.getRight() != null) {
                stack.push(stackCurrentNode.getRight());
            }

            if (stackCurrentNode.getLeft() != null) {
                stack.push(stackCurrentNode.getLeft());
            }
        }
    }

    public void deepVisitRecursively() {
        checkEmpty();

        deepVisitRecursivelySupport(root);
    }

    private void deepVisitRecursivelySupport(Node<T> node) {
        if (node != null) {
            System.out.println(node.getData());

            if (node.getLeft() != null) {
                deepVisitRecursivelySupport(node.getLeft());
            }

            if (node.getRight() != null) {
                deepVisitRecursivelySupport(node.getRight());
            }
        }
    }

    private void checkEmpty() {
        if (size == 0) {
            throw new NoSuchElementException("Tree is empty");
        }
    }
}