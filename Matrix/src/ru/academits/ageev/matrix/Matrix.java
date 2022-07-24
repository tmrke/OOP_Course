package ru.academits.ageev.matrix;

import ru.academits.ageev.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] vectorArray;

    public Matrix(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("matrix size can't be < 0");
        }

        vectorArray = new Vector[n];

        for (int i = 0; i < vectorArray.length; i++) {
            vectorArray[i] = new Vector(m);
        }
    }

    public Matrix(Matrix matrix) {
        if (matrix == null) {
            throw new NullPointerException("matrix can't be null");
        }

        vectorArray = matrix.vectorArray;
    }

    public Matrix(double[][] numberArray) {
        if (numberArray.length == 0) {
            throw new IllegalArgumentException("matrix size can't be < 0");
        }

        vectorArray = new Vector[numberArray.length];

        int maxSize = numberArray[0].length;

        for (int i = 0; i < numberArray.length; i++) {
            vectorArray[i] = new Vector(maxSize);

            for (int j = 0; j < numberArray[i].length; j++) {
                vectorArray[i].setComponentByIndex(j, numberArray[i][j]);
            }
        }
    }

    public Matrix(Vector[] vectorArray) {
        if (vectorArray == null) {
            throw new NullPointerException("matrix array can't be null");
        }

        if (vectorArray.length == 0) {
            throw new IllegalArgumentException("matrix size can't be < 0");
        }

        int maxSize = 0;

        for (Vector vector : vectorArray) {
            if (vector.getSize() > maxSize) {
                maxSize = vector.getSize();
            }
        }

        Vector emptyVector = new Vector(maxSize);

        for (Vector vector : vectorArray) {
            vector.add(emptyVector);
        }

        this.vectorArray = vectorArray;
    }

    public int getRowSize() {
        return vectorArray[0].getSize();
    }

    public int getColumnSize() {
        return vectorArray.length;
    }

    public Vector getVectorRow(int index) {
        if (index >= vectorArray.length || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index can't be >= length of vector or < 0");
        }

        return vectorArray[index];
    }

    public void setVectorRow(int index, Vector vector) {
        if (vector == null) {
            throw new NullPointerException("vector can't be null");
        }

        if (index >= vectorArray.length || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index can't be >= length of vector or < 0");
        }

        vectorArray[index] = vector;
    }

    public Vector getVectorColumn(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index can't be < 0");
        }

        Vector currentVector = new Vector(vectorArray.length);

        for (int i = 0; i < vectorArray.length; i++) {
            if (index > vectorArray[i].getSize()) {
                throw new IllegalArgumentException("index can't be > vector size");
            }
            currentVector.setComponentByIndex(i, vectorArray[i].getComponentByIndex(index));
        }

        return currentVector;
    }

    public void transpose() {
        Matrix tempMatrix = new Matrix(getRowSize(), getColumnSize());

        for (int i = 0; i < tempMatrix.getColumnSize(); i++) {
            tempMatrix.setVectorRow(i, this.getVectorColumn(i));
        }

        this.vectorArray = tempMatrix.vectorArray;
    }

    public void multiplyByScalar(double scalar) {
        for (Vector vector : vectorArray) {
            for (int i = 0; i < vector.getSize(); i++) {
                vector.setComponentByIndex(i, vector.getComponentByIndex(i) * scalar);
            }
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(vectorArray);
    }
}
