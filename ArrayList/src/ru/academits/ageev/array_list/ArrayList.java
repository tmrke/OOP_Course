package ru.academits.ageev.array_list;

import java.util.*;

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

    @Override
    public T set(int index, T item) {
        checkIndex(index);

        T previouslyItem = itemsArray[index];
        itemsArray[index] = item;

        return previouslyItem;
    }

    @Override
    public boolean add(T item) {
        if (length >= itemsArray.length) {
            ensureCapacity();
        }

        itemsArray[length] = item;
        length++;

        return true;
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
    public boolean addAll(int index, Collection<? extends T> collection) {
        checkIndex(index);

        if (length + collection.size() >= itemsArray.length) {
            ensureCapacity();
        }

        boolean hasChange = false;
        //noinspection unchecked
        T[] currentArray = (T[]) collection.toArray();

        for (int i = index; i < collection.size(); i++) {
            if (!get(i).equals(currentArray[i])) {
                hasChange = true;
            }

            add(currentArray[i]);
        }

        return hasChange;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        if (length + collection.size() >= itemsArray.length) {
            ensureCapacity();
        }

        boolean hasChange = false;
        //noinspection unchecked
        T[] currentArray = (T[]) collection.toArray();

        for (int i = 0; i < collection.size(); i++) {
            if (!get(i).equals(currentArray[i])) {
                hasChange = true;
            }

            add(currentArray[i]);
        }

        return hasChange;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        T previouslyItem = itemsArray[index];

        if (itemsArray.length - 1 - index >= 0) {
            System.arraycopy(itemsArray, index + 1, itemsArray, index, itemsArray.length - 1 - index);
        }

        itemsArray[length - 1] = null;
        length--;

        return previouslyItem;
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
    public boolean removeAll(Collection<?> list) {
        if (isEmpty() || list.size() == 0) {
            return false;
        }

        boolean hasChange = false;
        //noinspection unchecked
        T[] currentArray = (T[]) list.toArray();

        int i = 0, j = 0;
        int listSize = list.size();

        while (i < length) {
            while (j < listSize) {
                if (itemsArray[i] == null && currentArray[j] == null || Objects.equals(itemsArray[i], currentArray[j])) {
                    remove(i);
                    hasChange = true;
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

        return hasChange;
    }

    @Override
    public boolean retainAll(Collection<?> list) {
        if (isEmpty() || list.size() == 0) {
            return false;
        }

        boolean hasChange = false;
        //noinspection unchecked
        T[] currentArray = (T[]) list.toArray();

        int i = 0, j = 0;
        int listSize = list.size();

        while (i < length) {
            while (j < listSize && i < length) {
                if (itemsArray[i] == null && currentArray[j] == null || Objects.equals(itemsArray[i], currentArray[j])) {
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
            hasChange = true;
            j = 0;
        }
        return hasChange;
    }

    @Override
    public boolean contains(Object o) {
        if (isEmpty()) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (itemsArray[i] == null && o == null) {
                return true;
            }

            assert itemsArray[i] != null;

            //noinspection unchecked
            T t = (T) o;

            if (itemsArray[i].equals(t)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> list) {
        if (list.size() > length) {
            return false;
        }

        //noinspection unchecked
        T[] currentArray = (T[]) list.toArray();

        for (int i = 0; i < list.size(); i++) {
            if (!this.contains(currentArray[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < length; i++) {
            if (itemsArray[i] == null && o == null) {
                return i;
            }

            assert itemsArray[i] != null;
            //noinspection unchecked
            T t = (T) o;

            if (itemsArray[i].equals(t)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = length - 1; i >= 0; i--) {
            if (itemsArray[i] == null && o == null) {
                return i;
            }

            assert itemsArray[i] != null;
            //noinspection unchecked
            T t = (T) o;

            if (itemsArray[i].equals(t)) {
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

        //noinspection unchecked
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

    //===============================================================================================================
    //===============================================================================================================
    //===============================================================================================================


    @Override
    public void sort(Comparator<? super T> c) {
        List.super.sort(c);
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public Spliterator<T> spliterator() {
        return List.super.spliterator();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
}
