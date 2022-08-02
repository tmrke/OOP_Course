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

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Vectors array length can't be = 0");
        }

        int maxSize = 0;

        for (Vector vector : vectors) {
            if (vector.getSize() > maxSize) {
                maxSize = vector.getSize();
            }
        }

        strings = new Vector[vectors.length];

        for (int i = 0; i < vectors.length; i++) {
            strings[i] = new Vector(maxSize);
            strings[i].add(vectors[i]);
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
            vector.multiplyByScalar(scalar);
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

    private static double[][] generateSubArray(double[][] numbersArray, int j1) {
        int size = numbersArray.length;
        double[][] subArray = new double[size - 1][];

        for (int k = 0; k < (size - 1); k++) {
            subArray[k] = new double[size - 1];
        }

        for (int i = 1; i < size; i++) {
            int j2 = 0;

            for (int j = 0; j < size; j++) {
                if (j == j1) {
                    continue;
                }

                subArray[i - 1][j2] = numbersArray[i][j];
                j2++;
            }
        }

        return subArray;
    }

    public double getDeterminant() {
        if (getRowCount() != getColumnCount()) {
            throw new IllegalArgumentException("Matrix is not square; Row count = " + getRowCount() + "; Column count = " + getColumnCount() + ".");
        }

        if (getColumnCount() == 0) {
            throw new IllegalArgumentException("Matrix column size can't be == 0, columnsCount = " + getColumnCount() + ";");
        }

        if (getRowCount() == 0) {
            throw new IllegalArgumentException("Matrix row size can't be == 0, rowCount = " + getRowCount() + ";");
        }

        return getDeterminantRecursively(getNumbersArray(), strings.length);
    }

    private double[][] getNumbersArray() {
        int maxSize = 0;

        for (Vector vector : strings) {
            if (vector.getSize() > maxSize) {
                maxSize = vector.getSize();
            }
        }

        double[][] numbersArray = new double[strings.length][maxSize];

        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].getSize(); j++) {
                numbersArray[i][j] = strings[i].getComponentByIndex(j);
            }
        }

        return numbersArray;
    }

    private double getDeterminantRecursively(double[][] numbersArray, int size) {
        double determinant;

        if (size == 1) {
            return numbersArray[0][0];
        }

        if (size == 2) {
            return numbersArray[0][0] * numbersArray[1][1] - numbersArray[1][0] * numbersArray[0][1];
        }

        determinant = 0;

        for (int j1 = 0; j1 < size; j1++) {
            double[][] subArray = generateSubArray(numbersArray, j1);
            determinant += Math.pow(-1.0, 1.0 + j1 + 1.0) * numbersArray[0][j1] * getDeterminantRecursively(subArray, size - 1);
        }

        return determinant;
    }

    public void add(Matrix matrix) {
        if (getRowCount() != getColumnCount()) {
            throw new IllegalArgumentException("Matrix is not square; Row count = " + getRowCount() + "; Column count = " + getColumnCount() + ".");
        }

        if (getColumnCount() == 0) {
            throw new IllegalArgumentException("Matrix column size can't be == 0, columnsCount = " + getColumnCount() + ";");
        }

        if (getRowCount() == 0) {
            throw new IllegalArgumentException("Matrix row size can't be == 0, rowCount = " + getRowCount() + ";");
        }

        for (int i = 0; i < strings.length; i++) {
            strings[i].add(matrix.getRow(i));
        }
    }

    public void subtract(Matrix matrix) {
        if (getRowCount() != getColumnCount()) {
            throw new IllegalArgumentException("Matrix is not square; Row count = " + getRowCount() + "; Column count = " + getColumnCount() + ".");
        }

        if (getColumnCount() == 0) {
            throw new IllegalArgumentException("Matrix column size can't be == 0, columnsCount = " + getColumnCount() + ";");
        }

        if (getRowCount() == 0) {
            throw new IllegalArgumentException("Matrix row size can't be == 0, rowCount = " + getRowCount() + ";");
        }

        for (int i = 0; i < strings.length; i++) {
            strings[i].subtract(matrix.getRow(i));
        }
    }

    public void multiplyByVector(Vector vector) {
        if (vector.getSize() != getColumnCount()) {
            throw new IllegalArgumentException("Vector size can be = matrix size; vector size = " + vector.getSize());
        }

        Vector resultColumnVector = new Vector(strings.length);

        for (int i = 0; i < strings.length; i++) {
            int rowSumNumbers = 0;

            for (int j = 0; j < vector.getSize(); j++) {
                rowSumNumbers += strings[i].getComponentByIndex(j) * vector.getComponentByIndex(j);
            }

            resultColumnVector.setComponentByIndex(i, rowSumNumbers);
        }

        strings = new Vector[]{resultColumnVector};
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowCount() != matrix2.getRowCount()) {
            throw new IllegalArgumentException("row count matrix 1 != row count matrix 2");
        }

        if (matrix1.getColumnCount() != matrix2.getColumnCount()) {
            throw new IllegalArgumentException("column count of the matrix 1 != column count of the matrix 2");
        }

        Matrix matrixSum = new Matrix(matrix1.getRowCount(), matrix1.getColumnCount());

        for (int i = 0; i < matrixSum.getRowCount(); i++) {
            matrixSum.setRow(i, Vector.getSum(matrix1.getRow(i), matrix2.getRow(i)));
        }

        return matrixSum;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowCount() != matrix2.getRowCount()) {
            throw new IllegalArgumentException("row count matrix 1 != row count matrix 2");
        }

        if (matrix1.getColumnCount() != matrix2.getColumnCount()) {
            throw new IllegalArgumentException("column count of the matrix 1 != column count of the matrix 2");
        }

        Matrix matrixDifference = new Matrix(matrix1.getRowCount(), matrix1.getColumnCount());

        for (int i = 0; i < matrixDifference.getRowCount(); i++) {
            matrixDifference.setRow(i, Vector.getDifference(matrix1.getRow(i), matrix2.getRow(i)));
        }

        return matrixDifference;
    }

    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnCount() != matrix2.getRowCount()) {
            throw new IllegalArgumentException("Column count matrix 1 != row count matrix 2");
        }

        Matrix matrixMultiply = new Matrix(matrix1.getRowCount(), matrix2.getColumnCount());

        for (int i = 0; i < matrix1.getRowCount(); i++) {
            for (int j = 0; j < matrix2.getColumnCount(); j++) {
                matrixMultiply.strings[i].setComponentByIndex(j, Vector.getScalarMultiplication(matrix1.getRow(i), matrix2.getColumn(j)));
            }
        }

        return matrixMultiply;
    }
}