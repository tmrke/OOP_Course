package ru.academits.ageev.vector;

import java.util.Arrays;

public class Vector {
    private double[] array;
    private int n;

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("vector size can't be <= 0");
        }

        array = new double[n];
        this.n = n;
    }

    public Vector(double[] array) {
        if (array == null) {
            throw new NullPointerException("vector array can't be null");
        }

        if (array.length == 0) {
            throw new IllegalArgumentException("vector size can't be <= 0");
        }

        this.array = array;
        n = array.length;
    }

    public Vector(Vector vector) {
        if (vector == null) {
            throw new NullPointerException("vector can't be null");
        }

        if (vector.getSize() <= 0) {
            throw new IllegalArgumentException("vector size can't be <= 0");
        }

        this.array = vector.array;
        this.n = vector.n;
    }

    public Vector(int n, double[] array) {
        if (array == null) {
            throw new NullPointerException("vector array can't be null");
        }

        if (array.length == 0 || n <= 0) {
            throw new IllegalArgumentException("vector size can't be <= 0");
        }

        this.n = n;
        this.array = Arrays.copyOf(array, n);
    }

    public void setN(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("vector size can't be <= 0");
        }

        this.n = n;
        this.array = Arrays.copyOf(array, n);
    }

    public void add(Vector vector) {
        int maxLength = Math.max(array.length, vector.array.length);
        double[] tempThis = Arrays.copyOf(array, maxLength);
        double[] temp = Arrays.copyOf(vector.array, maxLength);

        for (int i = 0; i < tempThis.length; i++) {
            tempThis[i] += temp[i];
        }

        array = tempThis;
        setN(array.length);
    }

    public void subtract(Vector vector) {
        int maxLength = Math.max(array.length, vector.array.length);
        double[] tempThis = Arrays.copyOf(array, maxLength);
        double[] temp = Arrays.copyOf(vector.array, maxLength);

        for (int i = 0; i < maxLength; i++) {
            tempThis[i] -= temp[i];
        }

        array = tempThis;
        setN(array.length);
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
        return n;
    }

    public double getComponentByIndex(int index) {
        if (index > this.getSize()) {
            throw new ArrayIndexOutOfBoundsException("index can't be >= vector size");
        }

        return array[index];
    }

    public void setComponentByIndex(int index, double value) {
        if (index > this.getSize()) {
            throw new ArrayIndexOutOfBoundsException("index can't be >= vector size");
        }

        this.array[index] = value;
    }

    public static Vector add(Vector vector1, Vector vector2) {
        if (vector1 == null || vector2 == null) {
            throw new NullPointerException("vector array can't be null");
        }

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
        if (vector1 == null || vector2 == null) {
            throw new NullPointerException("vector array can't be null");
        }

        int maxN = Math.max(vector1.getSize(), vector2.getSize());
        Vector resultVector = new Vector(maxN);
        double[] temp1 = Arrays.copyOf(vector1.array, maxN);
        double[] temp2 = Arrays.copyOf(vector2.array, maxN);

        for (int i = 0; i < maxN; i++) {
            resultVector.array[i] = temp1[i] - temp2[i];
        }

        return resultVector;
    }

    public static Vector multiply(Vector vector, double number) {
        if (vector == null) {
            throw new NullPointerException("vector array can't be null");
        }

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