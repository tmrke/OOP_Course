package ru.academits.ageev.hash_table;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private static final int DEFAULT_ARRAY_LENGTH = 10;

    private final ArrayList<E>[] lists;
    private int modCount;
    private int size;

    public HashTable() {
        //noinspection unchecked
        lists = new ArrayList[DEFAULT_ARRAY_LENGTH];
    }

    public HashTable(int arrayLength) {
        if (arrayLength <= 0) {
            throw new IllegalArgumentException("ArrayLength can't be <= 0; arrayLength = " + arrayLength);
        }

        //noinspection unchecked
        lists = new ArrayList[arrayLength];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (isEmpty()) {
            return false;
        }

        int objectIndex = getIndex(o);

        return lists[objectIndex] != null && lists[objectIndex].contains(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        //noinspection unchecked
        HashTable<E> hashTable = (HashTable<E>) o;

        return Arrays.equals(lists, hashTable.lists);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(lists);
    }

    private class HashTableIterator implements Iterator<E> {
        private final int startModCount = modCount;
        private int arrayCurrentIndex;
        private int listCurrentIndex = -1;
        private int passedItemsCount;

        @Override
        public boolean hasNext() {
            return passedItemsCount < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("List are no more elements");
            }

            if (startModCount != modCount) {
                throw new ConcurrentModificationException("HashTable has change during the runtime");
            }

            while (hasNext()) {
                if (lists[arrayCurrentIndex] == null) {
                    arrayCurrentIndex++;
                } else {
                    listCurrentIndex++;

                    if (listCurrentIndex >= lists[arrayCurrentIndex].size()) {
                        arrayCurrentIndex++;
                        listCurrentIndex = -1;
                    } else {
                        passedItemsCount++;

                        return lists[arrayCurrentIndex].get(listCurrentIndex);
                    }
                }
            }

            return null;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[lists.length];
        int i = 0;

        for (E e : this) {
            array[i] = e;
            i++;
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(toArray(), size, a.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(toArray(), 0, a, 0, size);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E item) {
        int index = getIndex(item);

        if (lists[index] == null) {
            lists[index] = new ArrayList<>();
        }

        lists[index].add(item);
        size++;
        modCount++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }

        modCount++;
        size--;

        return lists[getIndex(o)].remove(o);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        for (E item : collection) {
            add(item);
        }

        return true;
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }

        for (ArrayList<E> arrayList : lists) {
            if (arrayList != null) {
                arrayList.clear();
            }
        }

        modCount++;
        size = 0;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            return false;
        }

        if (isEmpty()) {
            return false;
        }

        boolean hasChange = false;
        int startListSize;
        int endListSize;

        for (ArrayList<E> list : lists) {
            if (list != null) {
                startListSize = list.size();

                if (list.retainAll(collection)) {
                    hasChange = true;
                }

                endListSize = list.size();
                size -= startListSize - endListSize;
            }
        }

        if (hasChange) {
            modCount++;
        }

        return hasChange;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (isEmpty() || collection.isEmpty()) {
            return false;
        }

        boolean hasChange = false;
        int startListSize;
        int endListSize;

        for (ArrayList<E> list : lists) {
            if (list != null) {
                startListSize = list.size();

                if (list.removeAll(collection)) {
                    hasChange = true;
                }

                endListSize = list.size();
                size -= startListSize - endListSize;
            }
        }

        if (hasChange) {
            modCount++;
        }

        return hasChange;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object collectionItem : collection) {
            if (!contains(collectionItem)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{}";
        }

        StringBuilder stringBuilder = new StringBuilder("{");

        for (int i = 0; i < lists.length - 1; i++) {
            stringBuilder.append(lists[i]).append(", ");
        }

        return stringBuilder.append(lists[lists.length - 1]).append("}").toString();
    }

    private int getIndex(Object o) {
        if (o == null) {
            return (int) (Math.random() * lists.length);
        }

        return Math.abs(o.hashCode() % lists.length);
    }
}