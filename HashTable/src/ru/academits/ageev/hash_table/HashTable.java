package ru.academits.ageev.hash_table;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private static final int DEFAULT_ARRAY_LENGTH = 10;

    private ArrayList<E>[] lists;
    private int modCount;
    private int size;

    public HashTable() {
        //noinspection unchecked
        lists = new ArrayList[DEFAULT_ARRAY_LENGTH];
    }

    public HashTable(int arrayLength) {
        if (arrayLength < 0) {
            throw new IllegalArgumentException("ArrayLength can't be < 0; arrayLength = " + arrayLength);
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

        int objectIndex = getHashTableObjectIndex(o);

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

    private class HashTableIterator implements Iterator<E> {            //TODO переделать!
        private int currentIndex = -1;
        private final int currentModCount = modCount;

        @Override
        public boolean hasNext() {
            while (currentIndex + 1 < lists.length && lists[currentIndex + 1] == null) {
                currentIndex++;
            }

            return currentIndex + 1 < lists.length && lists[currentIndex + 1].iterator().hasNext();
        }

        @Override
        public E next() {
            if (!hasNext() && !lists[currentIndex].iterator().hasNext()) {
                throw new NoSuchElementException("List are no more elements");
            }

            if (currentModCount != modCount) {
                throw new ConcurrentModificationException("HashTable has change during the runtime");
            }

            currentIndex++;

            while (currentIndex < lists.length - 1 && lists[currentIndex] == null) {
                currentIndex++;
            }

            return lists[currentIndex].iterator().next();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        //noinspection unchecked
        E[] array = (E[]) new Object[size];
        int i = 0;

        for (E e : this) {
            array[i] = e;
            i++;
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < lists.length) {
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
        if (item == null) {
            return false;
        }

        int index = getHashTableObjectIndex(item);

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
        for (ArrayList<E> list : lists) {
            if (list != null && list.remove(o)) {
                size--;

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        //noinspection unchecked
        for (E item : (Iterable<E>) collection) {
            add(item);
            modCount++;
        }

        size += collection.size();

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
            return true;
        }

        if (isEmpty()) {
            return false;
        }

        boolean hasChange = false;

        for (ArrayList<E> list : lists) {
            if (list != null && list.retainAll(collection)) {
                hasChange = true;
                modCount++;
            }
        }

        return hasChange;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (isEmpty() || collection.isEmpty()) {
            return false;
        }

        boolean hasChange = false;

        for (ArrayList<E> list : lists) {
            if (list.removeAll(collection)) {
                size--;
                modCount++;
                hasChange = true;
            }
        }

        return hasChange;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            return true;
        }

        if (isEmpty()) {
            return false;
        }

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

    private int getHashTableObjectIndex(Object o) {
        return Math.abs(o.hashCode() % lists.length);
    }
}