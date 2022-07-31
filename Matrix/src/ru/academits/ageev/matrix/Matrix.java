package ru.academits.ageev.matrix;

import ru.academits.ageev.vector.Vector;

public class Matrix {
    private Vector[] strings;

    public Matrix(int rowsCount, int columnsCount) {
        if (columnsCount <= 0) {
            throw new IllegalArgumentException("Matrix column size can't be < 0, columnsCount = " + columnsCount + ";");
        }

        if (rowsCount <= 0) {
            throw new IllegalArgumentException("Matrix column size can't be < 0, rowsCount = " + rowsCount + ";");
        }

        strings = new Vector[rowsCount];

        for (int i = 0; i < strings.length; i++) {
            strings[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        strings = new Vector[matrix.strings.length];
        System.arraycopy(matrix.strings, 0, strings, 0, strings.length);
    }

    public Matrix(double[][] numbersArray) {
        if (numbersArray.length == 0) {
            throw new IllegalArgumentException("Numbers array length can't be = 0");
        }

        strings = new Vector[numbersArray.length];

        int maxSize = 0;

        for (double[] array : numbersArray) {
            if (array.length > maxSize) {
                maxSize = array.length;
            }
        }

        for (int i = 0; i < numbersArray.length; i++) {
            strings[i] = new Vector(maxSize, numbersArray[i]);
        }
    }

    public Matrix(Vector[] vectorsArray) {
        if (vectorsArray.length == 0) {
            throw new IllegalArgumentException("Vectors array length can't be = 0");
        }

        int maxSize = 0;

        for (Vector vector : vectorsArray) {
            if (vector.getSize() > maxSize) {
                maxSize = vector.getSize();
            }
        }

        strings = new Vector[vectorsArray.length];

        for (int i = 0; i < strings.length; i++) {
            strings[i] = new Vector(maxSize, vectorsArray[i].getVectorsArray());
        }
    }

    public int getRowCount() {
        return strings.length;
    }

    public int getColumnCount() {
        int maxRowCount = 0;

        for (Vector vector : strings) {
            if (vector.getSize() > maxRowCount) {
                maxRowCount = vector.getSize();
            }
        }

        return maxRowCount;
    }

    public Vector getRow(int index) {
        if (index < 0 || index >= strings.length) {
            throw new IndexOutOfBoundsException("Index of row = " + index
                    + "; Index of row can't be < 0 or >= " + getRowCount() + ";");
        }

        return new Vector(strings[index]);
    }

    public void setRow(int index, Vector vector) {
        if (index < 0 || index >= strings.length) {
            throw new IndexOutOfBoundsException("Index of row = " + index
                    + "; Index of row can't be < 0 or >= " + getRowCount() + ";");
        }

        if (vector.getSize() != strings[index].getSize()) {
            throw new IllegalArgumentException("Size of the vector = " + vector.getSize()
                    + "; Size of the vector can't be different from the size of the matrix strings");
        }

        strings[index] = new Vector(vector);
    }

    public Vector getColumn(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index of column = " + index
                    + "; Index of column can't be < 0 or >= " + getColumnCount() + ";");
        }

        Vector columnVector = new Vector(getRowCount());

        for (int i = 0; i < getRowCount(); i++) {
            columnVector.setComponentByIndex(i, strings[i].getComponentByIndex(index));
        }

        return columnVector;
    }

    public void transpose() {
        Vector[] transposedMatrixStrings = new Vector[getColumnCount()];

        for (int i = 0; i < getColumnCount(); i++) {
            transposedMatrixStrings[i] = getColumn(i);
        }

        strings = new Vector[getColumnCount()];

        if (strings.length >= 0) System.arraycopy(transposedMatrixStrings, 0, strings, 0, strings.length);
    }

    public void multiplyByScalar(double scalar) {
        for (Vector vector : strings) {
            vector.multiply(scalar);
        }
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder("{ ");

        for (int i = 0; i < getRowCount() - 1; i++) {
            resultString.append(strings[i].toString()).append(", ");
        }

        resultString.append(strings[getRowCount() - 1].toString()).append(" }");

        return resultString.toString();
    }
}
