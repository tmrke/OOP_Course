package ru.academits.ageev.array_list;

import java.util.Arrays;

public class ArrayList<T> implements List<T> {
    private T[] itemsArray;
    private int length;

    public ArrayList() {
        int defaultCapacity = 10;
        //noinspection unchecked
        itemsArray = (T[]) new Object[defaultCapacity];
    }

    public ArrayList(int capacity) {
        //noinspection unchecked
        itemsArray = (T[]) new Object[capacity];
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        return itemsArray[index];
    }

    public void set(int index, T item) {
        checkIndex(index);
        itemsArray[index] = item;
    }

    @Override
    public void add(T item) {
        if (length >= itemsArray.length) {
            ensureCapacity();
        }

        itemsArray[length] = item;
        length++;
    }

    @Override
    public void add(int index, T item) {
        if (index > length) {
            throw new IndexOutOfBoundsException("Index can't be > " + length + "; index = " + index);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index can't be < 0; index = " + index);
        }

        if (index >= itemsArray.length) {
            ensureCapacity();
        }

        for (int i = length; i > index; i--) {
            itemsArray[i] = itemsArray[i - 1];
        }

        itemsArray[index] = item;
        length++;
    }

    private void ensureCapacity() {
        itemsArray = Arrays.copyOf(itemsArray, itemsArray.length * 2);
    }

    @Override
    public void addAll(List<T> list) {
        if (length + list.size() >= itemsArray.length) {
            ensureCapacity();
        }

        for (int i = 0; i < list.size(); i++) {
            //noinspection unchecked
            add((T) list.get(i));
        }
    }

    @Override
    public void remove(int index) {
        checkIndex(index);

        if (itemsArray.length - 1 - index >= 0) {
            System.arraycopy(itemsArray, index + 1, itemsArray, index, itemsArray.length - 1 - index);
        }

        itemsArray[length - 1] = null;
        length--;
    }

    @Override
    public boolean remove(Object item) {
        for (int i = 0; i < length; i++) {
            if (itemsArray[i].equals(item)) {
                remove(i);

                return true;
            }
        }

        return false;
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }

        for (int i = 0; i < length; i++) {
            itemsArray[i] = null;
        }
    }

    @Override
    public void removeAll(List<T> list) {
        if (isEmpty() || list.size() == 0) {
            return;
        }

        int i = 0, j = 0;
        int listSize = list.size();

        while (i < length) {
            while (j < listSize) {
                if (itemsArray[i] == null && list.get(j) == null || itemsArray[i].equals(list.get(j))) {
                    remove(i);
                    j = 0;

                    if (i == length) {
                        break;
                    }
                } else {
                    j++;
                }
            }

            i++;
            j = 0;
        }
    }

    @Override
    public void retainAll(List<T> list) {
        if (isEmpty() || list.size() == 0) {
            return;
        }

        int i = 0, j = 0;
        int listSize = list.size();

        while (i < length) {
            while (j < listSize && i < length) {
                if (itemsArray[i] == null && list.get(j) == null || itemsArray[i].equals(list.get(j))) {
                    i++;
                    j = 0;
                } else {
                    j++;
                }
            }

            if (i >= length) {
                break;
            }

            remove(i);
            j = 0;
        }
    }

    @Override
    public boolean contains(T item) {
        if (isEmpty()) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (itemsArray[i] == null && item == null) {
                return true;
            }

            assert itemsArray[i] != null;

            if (itemsArray[i].equals(item)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(List<T> list) {
        if (list.size() > length) {
            return false;
        }

        for (int i = 0; i < list.size(); i++) {
            if (!this.contains((T) list.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void replaceAll(T item1, T item2) {
        if (isEmpty()) {
            return;
        }

        for (int i = 0; i < length; i++) {
            if (itemsArray[i] == null && item1 == null) {
                set(i, item2);
            }

            assert itemsArray[i] != null;

            if (itemsArray[i].equals(item1)) {
                set(i, item2);
            }
        }
    }

    @Override
    public int indexOf(T item) {
        for (int i = 0; i < length; i++) {
            if (itemsArray[i] == null && item == null) {
                return i;
            }

            assert itemsArray[i] != null;

            if (itemsArray[i].equals(item)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(T item) {
        for (int i = length - 1; i >= 0; i--) {
            if (itemsArray[i] == null && item == null) {
                return i;
            }

            assert itemsArray[i] != null;

            if (itemsArray[i].equals(item)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public T[] toArray() {
        return Arrays.copyOf(itemsArray, length);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        if (isEmpty()) {
            return "{}";
        } else if (length == 1) {
            return "{" + itemsArray[0] + "}";
        }

        for (int i = 0; i < length - 1; i++) {
            stringBuilder.append(itemsArray[i]);
            stringBuilder.append(", ");
        }

        stringBuilder.append(itemsArray[length - 1]).append("}");

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        T t = (T) o;

        return this.equals(t);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash *= prime + Arrays.hashCode(itemsArray);

        return hash;
    }

    private void checkIndex(int index) {
        if (index >= length) {
            throw new IndexOutOfBoundsException("Index can't be >= " + length + "; index = " + index);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index can't be < 0; index = " + index);
        }
    }
}
