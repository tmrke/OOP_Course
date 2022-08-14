package ru.academits.ageev.array_list;

public interface List<T> {

    int size();

    Object get(int index);

    void add(T item);

    void add(int index, T item);

    void addAll(List<T> list);

    void remove(int index);

    boolean remove(T item);

    void clear();

    void removeAll(List<T> list);

    void retainAll(List<T> list);

    boolean contains(T item);

    boolean containsAll(List<T> list);

    void replaceAll(T item1, T item2);

    int indexOf(T item);

    int lastIndexOf(T item);

    boolean isEmpty();

    T[] toArray();
}
