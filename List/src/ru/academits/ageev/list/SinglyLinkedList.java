package ru.academits.ageev.list;

import java.util.NoSuchElementException;
import java.util.Objects;

public class SinglyLinkedList<T> {
    private ListItem<T> head;
    private int size;

    public int getSize() {
        return size;
    }

    public void addFirst(T data) {
        head = new ListItem<>(data, head);
        size++;
    }

    public T getFirst() {
        isEmpty();

        return head.getData();
    }

    public T getByIndex(int index) {
        checkIndex(index);

        if (index == 0) {
            return head.getData();
        }

        return getItemByIndex(index).getData();
    }

    public T setByIndex(int index, T data) {
        checkIndex(index);

        ListItem<T> currentItem = getItemByIndex(index);
        T oldData = getItemByIndex(index).getData();
        currentItem.setData(data);

        return oldData;
    }

    public void addByIndex(int index, T data) {
        if (index > size) {
            throw new IndexOutOfBoundsException("Index = " + index + "; Index can't be > " + size);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index = " + index + "; Index can't be < 0");
        }

        if (index == 0) {
            addFirst(data);
        } else {
            ListItem<T> previousItem = getItemByIndex(index - 1);
            ListItem<T> currentItem = getItemByIndex(index);
            previousItem.setNext(new ListItem<>(data));
            previousItem.getNext().setNext(currentItem);
        }

        size++;
    }

    public T deleteByIndex(int index) {
        checkIndex(index);

        if (index == 0) {
            return deleteFirst();
        }

        ListItem<T> previousItem = getItemByIndex(index - 1);
        T deletableItemData = previousItem.getNext().getData();

        previousItem.setNext(previousItem.getNext().getNext());
        size--;

        return deletableItemData;
    }

    public T deleteFirst() {
        isEmpty();

        T deletedData = head.getData();
        head = head.getNext();

        size--;

        return deletedData;
    }

    public boolean deleteByData(T data) {
        if (size == 0) {
            return false;
        }

        if (Objects.equals(head.getData(), data)) {
            deleteFirst();
            size--;

            return true;
        }

        ListItem<T> currentItem = head;

        while (currentItem.getNext() != null) {
            if (Objects.equals(currentItem.getNext().getData(), data)) {
                currentItem.setNext(currentItem.getNext().getNext());
                size--;

                return true;
            }

            currentItem = currentItem.getNext();
        }

        return false;
    }

    public void reverse() {
        ListItem<T> currentItem = head;
        ListItem<T> previousItem = null;

        while (currentItem != null) {
            ListItem<T> temp = currentItem.getNext();

            currentItem.setNext(previousItem);
            previousItem = currentItem;
            head = currentItem;

            currentItem = temp;
        }
    }

    public SinglyLinkedList<T> copy() {
        SinglyLinkedList<T> copiedList = new SinglyLinkedList<>();
        ListItem<T> currentItem = head;

        copiedList.head = head;

        while (currentItem != null) {
            currentItem.setNext(currentItem.getNext());
            currentItem = currentItem.getNext();
        }

        return copiedList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        ListItem<T> currentItem = head;

        while (currentItem != null) {
            if (currentItem.getData() == null) {
                stringBuilder.append("null").append(", ");
            }

            stringBuilder.append(currentItem.getData()).append(", ");
            currentItem = currentItem.getNext();
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("}");

        return stringBuilder.toString();
    }

    private ListItem<T> getItemByIndex(int index) {
        ListItem<T> currentItem = head;

        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        return currentItem;
    }

    private void checkIndex(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + "; Index can't be >= " + size);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index = " + index + "; Index can't be < 0");
        }
    }

    private void isEmpty() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        }
    }
}