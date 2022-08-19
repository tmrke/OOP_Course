package ru.academits.ageev.hash_table;

import java.util.*;

public class HashTable<V> implements Collection<V> {
    private ArrayList<V>[] arrayLists;

    public HashTable() {
        int defaultSize = 10;
        //noinspection unchecked
        arrayLists = new ArrayList[defaultSize];
    }

    public HashTable(int size) {
        //noinspection unchecked
        arrayLists = new ArrayList[size];
    }

    private void ensureCapacity() {
        arrayLists = Arrays.copyOf(arrayLists, arrayLists.length * 2);
    }

    @Override
    public int size() {
        return arrayLists.length;
    }

    @Override
    public boolean isEmpty() {
        return arrayLists.length == 0;
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

    private class MyIterator implements Iterator<V> {
        private int currentIndex = -1;

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

            currentIndex++;

            while (currentIndex < arrayLists.length - 1 && arrayLists[currentIndex] == null) {
                currentIndex++;
            }

            return arrayLists[currentIndex].iterator().next();
        }
    }

    @Override
    public Iterator<V> iterator() {
        return new MyIterator();
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
    public boolean add(V item) {
        int index = Math.abs(item.hashCode() % arrayLists.length);

        if (arrayLists[index] == null) {
            arrayLists[index] = new ArrayList<>();
        }

        arrayLists[index].add(item);

        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < arrayLists.length; i++) {
            while (arrayLists[i] == null) {
                i++;
            }

            V currentItem = arrayLists[i].iterator().next();

            if (currentItem.equals(o)) {
                arrayLists[i].remove(currentItem);

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        for (V item : (Iterable<V>) c) {
            add(item);
        }

        return true;
    }

    @Override
    public void clear() {
        for (ArrayList<V> arrayList : arrayLists) {
            if (arrayList != null) {
                arrayList.clear();
            }
        }
    }

    @Override
    public boolean retainAll(Collection c) {
        for (Iterator<V> iterator = iterator(); iterator.hasNext(); ) {
            V currentItem = iterator.next();


        }

        return false;
    }


    //TODO===============================================================================================================
    //TODO===============================================================================================================
    //TODO===============================================================================================================


    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (int i = 0; i < arrayLists.length - 1; i++) {
            stringBuilder.append(arrayLists[i]).append(", ");
        }

        return stringBuilder.append(arrayLists[arrayLists.length - 1]).append("}").toString();
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}
