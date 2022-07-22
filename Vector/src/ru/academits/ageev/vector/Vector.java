package ru.academits.ageev.vector;

import java.util.Arrays;

public class Vector {       //TODO сделать исключения и тест в main
    private double[] array;

    private final int n;

    public Vector(int n) {
        array = new double[n];
        this.n = n;
    }

    public Vector(double[] array) {
        this.array = array;
        n = array.length;
    }

    public Vector(Vector vector) {
        this.array = vector.array;
        this.n = vector.n;
    }

    public Vector(int n, double[] array) {
        this.n = n;
        this.array = Arrays.copyOf(array, n);
    }

    public int getN() {
        return n;
    }

    public void add(Vector vector) {
        double[] temp = array.length > vector.array.length ? array : vector.array;

        for (int i = 0; i < Math.min(array.length, vector.array.length); i++) {
            temp[i] = array[i] + vector.array[i];
        }

        array = temp;
    }

    public void subtract(Vector vector) {
        int maxLength = Math.max(array.length, vector.array.length);
        double[] tempThis = Arrays.copyOf(array, maxLength);
        double[] temp = Arrays.copyOf(vector.array, maxLength);

        for (int i = 0; i < maxLength; i++) {
            tempThis[i] -= temp[i];
        }

        array = tempThis;
    }

    public void multiply(double number) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= number;
        }
    }

    public void reverse() {
        for (int i = 0; i < array.length; i++) {
            array[i] *= -1;
        }
    }

    public int getSize() {
        return array.length;
    }

    public double getComponentByIndex(int index) {
        return array[index];
    }

    public void setComponentByIndex(int index) {
        this.array[index] = array[index];
    }

    public static Vector add(Vector vector1, Vector vector2) {
        int maxN = Math.max(vector1.getSize(), vector2.getSize());
        double[] tempArray = vector1.array.length > vector2.array.length ? vector1.array : vector2.array;

        Vector resultVector = new Vector(maxN);
        resultVector.array = Arrays.copyOf(tempArray, maxN);

        for (int i = 0; i < Math.min(vector1.getSize(), vector2.getSize()); i++) {
            resultVector.array[i] = vector1.array[i] + vector2.array[i];
        }

        return resultVector;
    }

    public static Vector subtract(Vector vector1, Vector vector2) {
        int maxN = Math.max(vector1.getSize(), vector2.getSize());
        Vector resultVector = new Vector(maxN);

        double[] temp1 = vector1.array.length <= vector2.array.length ? Arrays.copyOf(vector1.array, maxN) :
                Arrays.copyOf(vector2.array, maxN);
        double[] temp2 = vector1.array.length > vector2.array.length ? Arrays.copyOf(vector1.array, maxN) :
                Arrays.copyOf(vector2.array, maxN);

        for (int i = 0; i < Math.min(vector1.getSize(), vector2.getSize()); i++) {
            resultVector.array[i] = temp1[i] - temp2[i];
        }

        return resultVector;
    }

    public static Vector multiply(Vector vector, double number) {
        Vector resultVector = new Vector(vector);

        for (int i = 0; i < resultVector.getSize(); i++) {
            resultVector.array[i] *= number;
        }

        return vector;
    }


    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Vector vector = (Vector) o;

        if (n != vector.n) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            if (array[i] != vector.array[i]) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + n;

        for (int i = 0; i < n; i++) {
            hash += Double.hashCode(array[i]);
        }

        return hash;
    }
}