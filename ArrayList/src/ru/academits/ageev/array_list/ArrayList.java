package ru.academits.ageev.array_list;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private E[] items;
    private int size;
    static final int defaultCapacity = 10;
    private int modCount = 0;

    public ArrayList() {
        //noinspection unchecked
        items = (E[]) new Object[defaultCapacity];
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IndexOutOfBoundsException("Capacity can't be < 0; capacity = " + capacity);
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

        E previouslyItem = items[index];
        items[index] = item;
        modCount++;

        return previouslyItem;
    }

    @Override
    public boolean add(E item) {
        add(size, item);
        modCount++;

        return true;
    }

    @Override
    public void add(int index, E item) {
        if (index == 0 && size == 0) {
            //noinspection unchecked
            items = (E[]) new Object[defaultCapacity];
        }

        if (index > size) {
            throw new IndexOutOfBoundsException("Index can't be > " + size + "; index = " + index);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index can't be < 0; index = " + index);
        }

        if (index >= items.length) {
            boostCapacity();
        }

        System.arraycopy(items, index, items, index + 1, size - index);

        items[index] = item;
        modCount++;
        size++;
    }

    private void boostCapacity() {
        if (size == 0) {
            //noinspection unchecked
            items = (E[]) new Object[defaultCapacity];
        }

        items = Arrays.copyOf(items, items.length * 2);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        if (index == 0 && size == 0) {
            //noinspection unchecked
            items = (E[]) new Object[defaultCapacity];
        }

        if (index > size) {
            throw new IndexOutOfBoundsException("Index can't be > " + size + "; index = " + index);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index can't be < 0; index = " + index);
        }

        int sizeCollection = collection.size();

        if (size + sizeCollection >= items.length) {
            items = Arrays.copyOf(items, size + sizeCollection);
        }

        System.arraycopy(items, index, items, index + sizeCollection, size - index);
        modCount++;

        int i = index;

        for (E item : collection) {
            items[i] = (E) item;
            i++;
            size++;
            modCount++;
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return addAll(0, collection);
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E previouslyItem = items[index];

        System.arraycopy(items, index + 1, items, index, items.length - 1 - index);
        items[size - 1] = null;
        modCount++;
        size--;

        return previouslyItem;
    }

    @Override
    public boolean remove(Object object) {
        if (indexOf(object) < 0) {
            return false;
        }

        modCount++;

        return Objects.equals(remove(indexOf(object)), object);
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }

        Arrays.fill(items, null);
        modCount++;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (isEmpty() || collection.size() == 0) {
            return false;
        }

        boolean hasChange = false;

        for (Object o : collection) {
            if (contains(o)) {
                remove(o);
                hasChange = true;
                modCount++;
            }
        }

        return hasChange;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            if (isEmpty()) {
                return false;
            }

            clear();
            modCount++;

            return true;
        }

        boolean hasChange = false;

        for (int i = 0; i < size; i++) {
            if (!collection.contains(items[i])) {
                remove(items[i]);
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
        if (collection.isEmpty() || isEmpty()) {
            return false;
        }

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
        if (a.length <= size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(items, size);
        }

        //noinspection unchecked
        return (T[]) Arrays.copyOf(items, a.length);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{}";
        }

        StringBuilder stringBuilder = new StringBuilder("{");
        int sizeLimit = size - 1;

        for (int i = 0; i < sizeLimit; i++) {
            stringBuilder.append(items[i]);
            stringBuilder.append(", ");
        }

        stringBuilder.append(items[sizeLimit]).append("}");

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

        return Arrays.equals(items, arrayList.items) && size == arrayList.size;
    }

    @Override
    public int hashCode() {
        final int prime = 37;

        return prime + Arrays.hashCode(Arrays.copyOf(items, size));
    }

    private void checkIndex(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index can't be >= " + size + "; index = " + index);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index can't be < 0; index = " + index);
        }
    }

    private class Iterator implements java.util.Iterator<E> {
        private int currentIndex = -1;
        private final int currentModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public E next() {
            if (currentIndex + 1 >= size) {
                throw new NoSuchElementException();
            }

            if (currentModCount != modCount) {
                throw new ConcurrentModificationException("List has change during the runtime");
            }

            currentIndex++;

            return items[currentIndex];
        }
    }

    @Override
    public java.util.Iterator<E> iterator() {
        return new Iterator();
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
