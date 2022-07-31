package ru.academits.ageev.vector;

import java.util.Arrays;

public class Vector {
    private double[] vectorsArray;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size = " + size + "; size can't be <= 0");
        }

        vectorsArray = new double[size];
    }

    public Vector(double[] vectorsArray) {
        if (vectorsArray == null) {
            throw new NullPointerException("VectorsArray is null; vectorsArray can't be null");
        }

        this.vectorsArray = Arrays.copyOf(vectorsArray, vectorsArray.length);
    }

    public Vector(Vector vector) {
        if (vector == null) {
            throw new NullPointerException("Vector is null; Vector can't be null");
        }

        vectorsArray = Arrays.copyOf(vector.vectorsArray, vector.vectorsArray.length);
    }

    public Vector(int size, double[] vectorsArray) {
        if (vectorsArray == null) {
            throw new NullPointerException("VectorsArray is null; vectorsArray can't be null");
        }

        if (size < 0) {
            throw new IllegalArgumentException("Size = " + size + "; size can't be < 0");
        }

        this.vectorsArray = Arrays.copyOf(vectorsArray, size);
    }

    public void add(Vector vector) {
        for (int i = 0; i < Math.min(vectorsArray.length, vector.vectorsArray.length); i++) {
            vectorsArray[i] += vector.vectorsArray[i];
        }

        if (vectorsArray.length < vector.vectorsArray.length) {
            vectorsArray = Arrays.copyOf(vectorsArray, vector.vectorsArray.length);
        }
    }

    public void subtract(Vector vector) {
        if (vectorsArray.length < vector.vectorsArray.length) {
            vectorsArray = Arrays.copyOf(vectorsArray, vector.vectorsArray.length);
        }

        for (int i = 0; i < vector.vectorsArray.length; i++) {
            vectorsArray[i] -= vector.vectorsArray[i];
        }
    }

    public void multiply(double number) {
        for (int i = 0; i < vectorsArray.length; i++) {
            vectorsArray[i] *= number;
        }
    }

    public void reverse() {
        multiply(-1);
    }

    public int getSize() {
        return vectorsArray.length;
    }

    public double getComponentByIndex(int index) {
        if (index >= vectorsArray.length || index < 0) {
            throw new IndexOutOfBoundsException("Index = " + index + "; index can't be >= vectorsArray.length or < 0");
        }

        return vectorsArray[index];
    }

    public void setComponentByIndex(int index, double value) {
        if (index >= vectorsArray.length || index < 0) {
            throw new IndexOutOfBoundsException("Index = " + index + "; index can't be >= vectorsArray.length or < 0");
        }

        vectorsArray[index] = value;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        if (vector1 == null) {
            throw new NullPointerException("Vector1 is null; vector1 can't be null");
        }

        if (vector2 == null) {
            throw new NullPointerException("Vector2 is null; vector2 can't be null");
        }

        Vector resultVector = new Vector(vector1);

        if (vector1.vectorsArray.length < vector2.vectorsArray.length) {
            resultVector.vectorsArray = Arrays.copyOf(vector1.vectorsArray, vector2.vectorsArray.length);
        }

        resultVector.add(vector2);

        return resultVector;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        if (vector1 == null) {
            throw new NullPointerException("Vector1 is null; vector1 can't be null");
        }

        if (vector2 == null) {
            throw new NullPointerException("Vector2 is null; vector2 can't be null");
        }

        Vector resultVector = new Vector(vector1);

        if (vector1.vectorsArray.length < vector2.vectorsArray.length) {
            resultVector.vectorsArray = Arrays.copyOf(vector1.vectorsArray, vector2.vectorsArray.length);
        }

        resultVector.subtract(vector2);

        return resultVector;
    }

    public static Vector multiply(Vector vector1, Vector vector2) {
        if (vector1 == null) {
            throw new NullPointerException("Vector1 is null; vector1 can't be null");
        }

        if (vector2 == null) {
            throw new NullPointerException("Vector2 is null; vector2 can't be null");
        }


        if (vector1.vectorsArray.length < vector2.vectorsArray.length) {
            Vector resultVector = new Vector(vector1);
            resultVector.vectorsArray = Arrays.copyOf(vector1.vectorsArray, vector2.vectorsArray.length);

            for (int i = 0; i < resultVector.vectorsArray.length; i++) {
                resultVector.vectorsArray[i] *= vector2.vectorsArray[i];
            }

            return resultVector;
        }

        Vector resultVector = new Vector(vector2);
        resultVector.vectorsArray = Arrays.copyOf(vector2.vectorsArray, vector1.vectorsArray.length);

        for (int i = 0; i < resultVector.vectorsArray.length; i++) {
            resultVector.vectorsArray[i] *= vector1.vectorsArray[i];
        }

        return resultVector;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder("{");

        for (int i = 0; i < vectorsArray.length - 1; i++) {
            resultString.append(vectorsArray[i]).append(", ");
        }

        resultString.append(vectorsArray[vectorsArray.length - 1]).append("}");

        return resultString.toString();
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

        return Arrays.equals(vectorsArray, vector.vectorsArray);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Arrays.hashCode(vectorsArray);

        return hash;
    }
}