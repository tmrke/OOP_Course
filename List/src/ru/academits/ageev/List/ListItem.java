package ru.academits.ageev.List;

public class ListItem<String> {
    private String data;
    private ListItem<String> next;

    public ListItem(String data) {
        this.data = data;
    }

    public ListItem(String data, ListItem<String> next) {
        this.data = data;
        this.next = next;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ListItem<String> getNext() {
        return next;
    }

    public void setNext(ListItem<String> next) {
        this.next = next;
    }

    @Override
    public java.lang.String toString() {
        return (java.lang.String) data;
    }
}