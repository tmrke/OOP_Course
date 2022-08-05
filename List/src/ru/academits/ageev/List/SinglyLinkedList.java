package ru.academits.ageev.List;

public class SinglyLinkedList<String> {
    private ListItem<String> head;
    private int count;

    public ListItem<String> getHead() {
        return head;
    }

    public int getSize() {
        return count;
    }

    public void addFirstElement(ListItem<String> listItem) {
        if (head != null) {
            listItem.setNext(head);
        }

        head = listItem;
        count++;
    }

    public String getFirstElementData() {
        return head.getData();
    }

    public String getElementDataByIndex(int index) {
        if (index >= count) {
            throw new IndexOutOfBoundsException("Index = " + index + "; Index can't be >= " + count);
        }

        if (index == 0) {
            return head.getData();
        }

        ListItem<String> currentElement = head;

        for (int i = 0; i < index; i++) {
            currentElement = currentElement.getNext();
        }

        return currentElement.getData();
    }

    public String setElementDataByIndex(int index, String data) {
        if (index >= count) {
            throw new IndexOutOfBoundsException("Index = " + index + "; Index can't be >= " + count);
        }

        ListItem<String> currentElement = head;

        for (int i = 0; i < index; i++) {
            currentElement = currentElement.getNext();
        }

        String oldData = currentElement.getData();
        currentElement.setData(data);

        return oldData;
    }

    public void setElementByIndex(int index, ListItem<String> listItem) {
        if (index > count) {
            throw new IndexOutOfBoundsException("Index = " + index + "; Index can't be > " + count);
        }

        if (index == 0) {
            addFirstElement(listItem);
        } else {
            ListItem<String> currentElement = new ListItem<>(head.getData(), head);

            for (int i = 0; i < index; i++) {
                currentElement = currentElement.getNext();
            }

            ListItem<String> temp = currentElement.getNext();
            currentElement.setNext(listItem);
            listItem.setNext(temp);

            count++;
        }
    }

    public String deleteElementByIndex(int index) {
        if (index >= count) {
            throw new IndexOutOfBoundsException("Index = " + index + "; Index can't be >= " + count);
        }

        if (index == 0) {
            String deletableString = head.getData();

            head = head.getNext();
            count--;

            return deletableString;
        }

        ListItem<String> currentElement = new ListItem<>(head.getData(), head);
        String deletableElementString = getElementDataByIndex(index);

        for (int i = 0; i < index; i++) {
            currentElement = currentElement.getNext();
        }


        currentElement.setNext(currentElement.getNext().getNext());
        count--;

        return deletableElementString;
    }

    public String deleteFirstElement() {
        String deletableString = head.getData();

        head = head.getNext();
        count--;

        return deletableString;
    }

    public boolean hasDeleteByData(String data) {
        ListItem<String> currentElement = new ListItem<>(head.getData(), head);

        for (int i = 0; i < count; i++) {
            currentElement = currentElement.getNext();

            if (currentElement.getData().equals(data)) {
                deleteElementByIndex(i);
                return true;
            }
        }

        return false;
    }

    public void reverse() {
        for (int i = count; i > 0; i--) {
            ListItem<String> currentElement = new ListItem<>(head.getData(), head);
            setElementByIndex(i, currentElement);
            deleteFirstElement();
        }
    }

    public SinglyLinkedList<String> copyOf() {
        SinglyLinkedList<String> copiedList = new SinglyLinkedList<>();

        ListItem<String> currentElement = head;
        copiedList.addFirstElement(new ListItem<>(currentElement.getData(), currentElement.getNext()));
        copiedList.reverse();

        return copiedList;
    }

    @Override
    public java.lang.String toString() {
        StringBuilder resultString = new StringBuilder("{ ");

        for (ListItem<String> currentElement = head; currentElement != null; currentElement = currentElement.getNext()) {
            resultString.append(currentElement.getData());

            if (currentElement.getNext() != null) {
                resultString.append(", ");
            }
        }

        resultString.append(" }");

        return resultString.toString();
    }
}