package ru.academits.ageev.hash_table;

import java.util.*;

public class HashTable<V> implements Collection<V> {
    private ArrayList<V>[] arrayLists;
    private static final int defaultSize = 10;
    private int modCount = 0;

    public HashTable() {
        //noinspection unchecked
        arrayLists = new ArrayList[defaultSize];
    }

    public HashTable(int size) {
        //noinspection unchecked
        arrayLists = new ArrayList[size];
    }

    private void boostCapacity() {
        arrayLists = Arrays.copyOf(arrayLists, arrayLists.length * 2);
    }

    @Override
    public int size() {
        return arrayLists.length;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < arrayLists.length; i++) {
            while (i < arrayLists.length && arrayLists[i] == null) {
                i++;
            }

            if (i < arrayLists.length && !arrayLists[i].isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean contains(Object o) {
        if (isEmpty()) {
            return false;
        }

        for (int i = 0; i < arrayLists.length; i++) {
            while (arrayLists[i] == null) {
                i++;
            }

            if (arrayLists[i].contains(o)) {
                return true;
            }
        }

        return false;
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
        HashTable<V> hashTable = (HashTable<V>) o;

        return Arrays.equals(arrayLists, hashTable.arrayLists);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arrayLists);
    }

    private class Iterator implements java.util.Iterator<V> {
        private int currentIndex = -1;
        private final int currentModCount = modCount;

        @Override
        public boolean hasNext() {
            while (currentIndex + 1 < arrayLists.length && arrayLists[currentIndex + 1] == null) {
                currentIndex++;
            }

            return currentIndex + 1 < arrayLists.length && arrayLists[currentIndex + 1].iterator().hasNext();
        }

        @Override
        public V next() {
            if (currentIndex + 1 >= arrayLists.length && !arrayLists[currentIndex].iterator().hasNext()) {
                throw new NoSuchElementException();
            }

            if (currentModCount != modCount) {
                throw new ConcurrentModificationException("HashTable has change during the runtime");
            }

            currentIndex++;

            while (currentIndex < arrayLists.length - 1 && arrayLists[currentIndex] == null) {
                currentIndex++;
            }

            return arrayLists[currentIndex].iterator().next();
        }
    }

    @Override
    public java.util.Iterator<V> iterator() {
        return new Iterator();
    }

    @Override
    public Object[] toArray() {
        ArrayList<V> currentArrayList = new ArrayList<>();

        for (ArrayList<V> arrayList : arrayLists) {
            if (arrayList != null) {
                currentArrayList.addAll(arrayList);
            }
        }

        return currentArrayList.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        ArrayList<V> currentArrayList = new ArrayList<>();

        for (ArrayList<V> arrayList : arrayLists) {
            if (arrayList != null) {
                currentArrayList.addAll(arrayList);
            }
        }

        if (a.length <= currentArrayList.size()) {
            return currentArrayList.toArray();
        }

        return currentArrayList.toArray(a);
    }

    @Override
    public boolean add(V item) {
        int index = Math.abs(item.hashCode() % arrayLists.length);

        if (arrayLists[index] == null) {
            arrayLists[index] = new ArrayList<>();
        }

        arrayLists[index].add(item);
        modCount++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < arrayLists.length; i++) {
            while (i < arrayLists.length && arrayLists[i] == null) {
                i++;
            }

            for (int j = 0; j < arrayLists[i].size(); j++) {

                V currentItem = arrayLists[i].get(j);

                if (Objects.equals(currentItem, o)) {
                    arrayLists[i].remove(currentItem);
                    modCount++;

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean addAll(Collection collection) {
        //noinspection unchecked
        for (V item : (Iterable<V>) collection) {
            add(item);
            modCount++;
        }

        return true;
    }

    @Override
    public void clear() {
        for (ArrayList<V> arrayList : arrayLists) {
            if (arrayList != null) {
                arrayList.clear();
                modCount++;
            }
        }
    }

    @Override
    public boolean retainAll(Collection collection) {
        if (isEmpty() || collection.isEmpty()) {
            return false;
        }

        boolean hasChange = false;

        for (int i = 0; i < arrayLists.length; i++) {
            while (arrayLists[i] == null) {
                i++;
            }

            for (int j = 0; j < arrayLists[i].size(); j++) {
                V currentItem = arrayLists[i].get(j);

                if (!collection.contains(currentItem)) {
                    remove(currentItem);
                    hasChange = true;
                    modCount++;
                }
            }
        }

        return hasChange;
    }

    @Override
    public boolean removeAll(Collection collection) {
        if (isEmpty() || collection.isEmpty()) {
            return false;
        }

        boolean hasChange = false;

        for (Object currentItem : collection) {
            if (contains(currentItem)) {
                remove(currentItem);
                hasChange = true;
                modCount++;
            }
        }

        return hasChange;
    }

    @Override
    public boolean containsAll(Collection collection) {
        if (isEmpty() || collection.isEmpty()) {
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

        for (int i = 0; i < arrayLists.length - 1; i++) {
            stringBuilder.append(arrayLists[i]).append(", ");
        }

        return stringBuilder.append(arrayLists[arrayLists.length - 1]).append("}").toString();
    }
}