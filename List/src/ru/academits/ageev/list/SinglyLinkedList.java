package ru.academits.ageev.list;

public class SinglyLinkedList<T> {
    private ListItem<T> head;
    private int count;

    public int getSize() {
        return count;
    }

    public void addFirst(T data) {
        head = new ListItem<>(data, head);
        count++;
    }

    public T getFirst() {
        emptyList();

        return head.getData();
    }

    public T getItemDataByIndex(int index) {
        indexValidation(index);

        if (index == 0) {
            return head.getData();
        }

        return getItemByIndex(index).getData();
    }

    public T setItemDataByIndex(int index, T data) {
        indexValidation(index);

        ListItem<T> currentItem = getItemByIndex(index);
        T oldData = getItemByIndex(index).getData();
        currentItem.setData(data);

        return oldData;
    }

    public void addItemDataByIndex(int index, T data) {
        if (index > count) {
            throw new IndexOutOfBoundsException("Index = " + index + "; Index can't be > " + count);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index = " + index + "; Index can't be < 0");
        }

        ListItem<T> addedItem = new ListItem<>(data);

        if (index == 0) {
            addFirst(data);
        } else if (index == count) {
            ListItem<T> currentItem = getItemByIndex(index - 1);
            currentItem.setNext(addedItem);
        } else {
            ListItem<T> currentItem = getItemByIndex(index - 1);
            ListItem<T> temp = getItemByIndex(index);
            currentItem.setNext(addedItem);
            getItemByIndex(index).setNext(temp);
        }

        count++;
    }

    public T deleteByIndex(int index) {
        indexValidation(index);

        if (index == 0) {
            T deletedData = head.getData();
            deleteFirst();

            return deletedData;
        }

        ListItem<T> currentItem = getItemByIndex(index);

        if (index == count - 1) {
            currentItem.setNext(null);
            getItemByIndex(index - 1).setNext(null);
        } else {
            getItemByIndex(index - 1).setNext(getItemByIndex(index).getNext());
        }

        count--;

        return currentItem.getData();
    }

    public T deleteFirst() {
        emptyList();

        T deletedData = head.getData();

        if (count == 1) {
            head = null;
        } else {
            head = head.getNext();
        }

        count--;

        return deletedData;
    }

    public boolean deleteByData(T data) {
        emptyList();

        ListItem<T> currentItem = head;

        for (int i = 0; currentItem != null; i++) {
            if (currentItem.getData() == null) {
                return data == null;
            }

            if (currentItem.getData().equals(data)) {
                deleteByIndex(i);
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

        while (currentItem != null) {
            copiedList.addFirst(currentItem.getData());
            currentItem = currentItem.getNext();
        }

        copiedList.reverse();

        return copiedList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        ListItem<T> currentItem = head;

        while (currentItem != null) {
            stringBuilder.append(currentItem.getData().toString());

            if (currentItem.getNext() != null) {
                stringBuilder.append(", ");
            }

            currentItem = currentItem.getNext();
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    private ListItem<T> getItemByIndex(int index) {
        ListItem<T> currentItem = head;

        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        return currentItem;
    }

    private void indexValidation(int index) {
        if (index >= count) {
            throw new IndexOutOfBoundsException("Index = " + index + "; Index can't be >= " + count);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index = " + index + "; Index can't be < 0");
        }
    }

    public void emptyList() {
        if (count == 0) {
            throw new NullPointerException("List is empty");
        }
    }
}