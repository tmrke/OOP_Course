package ru.academits.ageev.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size = " + size + "; size can't be <= 0");
        }

        components = new double[size];
    }

    public Vector(double[] components) {
        if (components == null) {
            throw new NullPointerException("Components is null; Components can't be null");
        }

        if (components.length == 0) {
            throw new IllegalArgumentException("Components length = " + components.length + "; Components length can't be = 0");
        }

        this.components = Arrays.copyOf(components, components.length);
    }

    public Vector(Vector vector) {
        if (vector == null) {
            throw new NullPointerException("Vector is null; Vector can't be null");
        }

        components = Arrays.copyOf(vector.components, vector.components.length);
    }

    public Vector(int size, double[] components) {
        if (components == null) {
            throw new NullPointerException("Components is null; Components can't be null");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Size = " + size + "; size can't be <= 0");
        }

        this.components = Arrays.copyOf(components, size);
    }

    public void add(Vector vector) {
        if (components.length < vector.components.length) {
            components = Arrays.copyOf(components, vector.components.length);
        }

        for (int i = 0; i < vector.components.length; i++) {
            components[i] += vector.components[i];
        }
    }

    public void subtract(Vector vector) {
        if (components.length < vector.components.length) {
            components = Arrays.copyOf(components, vector.components.length);
        }

        for (int i = 0; i < vector.components.length; i++) {
            components[i] -= vector.components[i];
        }
    }

    public void multiplyByScalar(double number) {
        for (int i = 0; i < components.length; i++) {
            components[i] *= number;
        }
    }

    public void reverse() {
        multiplyByScalar(-1);
    }

    public double getLength() {
        double lengthSquare = 0;

        for (double component : components) {
            lengthSquare += component * component;
        }

        return Math.sqrt(lengthSquare);
    }

    public int getSize() {
        return components.length;
    }

    public double getComponentByIndex(int index) {
        if (index < 0 || index >= components.length) {
            throw new IndexOutOfBoundsException("Index = " + index + "; index can't be < 0 or >= " + components.length);
        }

        return components[index];
    }

    public void setComponentByIndex(int index, double value) {
        if (index < 0 || index >= components.length) {
            throw new IndexOutOfBoundsException("Index = " + index + "; index can't be < 0 or >= " + components.length);
        }

        components[index] = value;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        if (vector1 == null) {
            throw new NullPointerException("Vector1 is null; vector1 can't be null");
        }

        if (vector2 == null) {
            throw new NullPointerException("Vector2 is null; vector2 can't be null");
        }

        Vector resultVector = new Vector(vector1);
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
        resultVector.subtract(vector2);

        return resultVector;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        if (vector1 == null) {
            throw new NullPointerException("Vector1 is null; vector1 can't be null");
        }

        if (vector2 == null) {
            throw new NullPointerException("Vector2 is null; vector2 can't be null");
        }

        double result = 0;

        int minVectorSize = Math.min(vector1.components.length, vector2.components.length);

        for (int i = 0; i < minVectorSize; i++) {
            result += vector1.components[i] * vector2.components[i];
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (int i = 0; i < components.length - 1; i++) {
            stringBuilder.append(components[i]).append(", ");
        }

        stringBuilder.append(components[components.length - 1]).append("}");

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

        Vector vector = (Vector) o;

        return Arrays.equals(components, vector.components);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        return hash * prime + Arrays.hashCode(components);
    }
}