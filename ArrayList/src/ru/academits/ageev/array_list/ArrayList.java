package ru.academits.ageev.array_list;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private E[] items;
    private int size;
    private int modCount;

    public ArrayList() {
        //noinspection unchecked
        items = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity can't be < 0; capacity = " + capacity);
        }

        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return items[index];
    }

    @Override
    public E set(int index, E item) {
        checkIndex(index);

        E oldItem = items[index];
        items[index] = item;

        return oldItem;
    }

    @Override
    public boolean add(E item) {
        add(size, item);

        return true;
    }

    @Override
    public void add(int index, E item) {
        if (index > size) {
            throw new IndexOutOfBoundsException("Index can't be > " + size + "; index = " + index);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index can't be < 0; index = " + index);
        }

        if (size >= items.length) {
            increaseCapacity();
        }

        System.arraycopy(items, index, items, index + 1, size - index);

        items[index] = item;
        modCount++;
        size++;
    }

    private void increaseCapacity() {
        if (items.length == 0) {
            //noinspection unchecked
            items = (E[]) new Object[DEFAULT_CAPACITY];

            return;
        }

        items = Arrays.copyOf(items, items.length * 2);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        if (index > size) {
            throw new IndexOutOfBoundsException("Index can't be > " + size + "; index = " + index);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index can't be < 0; index = " + index);
        }

        int finalSize = size + collection.size();
        ensureCapacity(finalSize);

        System.arraycopy(items, index, items, index + collection.size(), size - index);
        modCount++;

        int i = index;
        boolean hasChange = false;

        for (E item : collection) {
            items[i] = item;
            hasChange = true;
            i++;
        }

        size = finalSize;

        return hasChange;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return addAll(size, collection);
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedItem = items[index];

        System.arraycopy(items, index + 1, items, index, size - 1 - index);
        items[size - 1] = null;
        modCount++;
        size--;

        return removedItem;
    }

    @Override
    public boolean remove(Object object) {
        int index = indexOf(object);

        if (index < 0) {
            return false;
        }

        remove(index);

        return true;
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }

        Arrays.fill(items, 0, size, null);
        size = 0;
        modCount++;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (isEmpty() || collection.size() == 0) {
            return false;
        }

        boolean hasChange = false;

        for (int i = 0; i < size; i++) {
            if (collection.contains(items[i])) {
                remove(i);
                hasChange = true;
                i--;
            }
        }

        return hasChange;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (isEmpty()) {
            return false;
        }

        if (collection.isEmpty()) {
            clear();

            return true;
        }

        boolean hasChange = false;

        for (int i = 0; i < size; i++) {
            if (!collection.contains(items[i])) {
                remove(i);
                hasChange = true;
                i--;
            }
        }

        return hasChange;
    }

    @Override
    public boolean contains(Object object) {
        return indexOf(object) >= 0;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object o : collection) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < items.length) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(items, size, a.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(items, 0, a, 0, size);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{}";
        }

        StringBuilder stringBuilder = new StringBuilder("{");
        int maxIndex = size - 1;

        for (int i = 0; i < maxIndex; i++) {
            stringBuilder.append(items[i]).append(", ");
        }

        stringBuilder.append(items[maxIndex]).append("}");

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
        ArrayList<E> arrayList = (ArrayList<E>) o;

        return size == arrayList.size && equalsArrays(items, arrayList.items);
    }

    private boolean equalsArrays(E[] array1, E[] array2) {
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(array1[i], array2[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hashCode = 1;

        hashCode = prime * hashCode;

        for (int i = 0; i < size; i++) {
            hashCode += items[i].hashCode();
        }

        return hashCode;
    }

    private void checkIndex(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index can't be >= " + size + "; index = " + index);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index can't be < 0; index = " + index);
        }
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > items.length) {
            items = Arrays.copyOf(items, minCapacity);
        }
    }

    public void trimToSize() {
        if (items.length > size) {
            items = Arrays.copyOf(items, size);
        }
    }

    private class ArrayListIterator implements Iterator<E> {
        private int currentIndex = -1;
        private final int startModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("List are no more elements");
            }

            if (startModCount != modCount) {
                throw new ConcurrentModificationException("List has change during the runtime");
            }

            currentIndex++;

            return items[currentIndex];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    //===============================================================================================================
    //===============================================================================================================
    //===============================================================================================================

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}