package ru.academits.ageev.matrix;

import ru.academits.ageev.vector.Vector;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (columnsCount <= 0) {
            throw new IllegalArgumentException("Matrix column count can't be < 0, columnsCount = " + columnsCount);
        }

        if (rowsCount <= 0) {
            throw new IllegalArgumentException("Matrix row count can't be < 0, rowsCount = " + rowsCount);
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < matrix.getRowsCount(); i++) {
            rows[i] = new Vector(matrix.getRow(i));
        }
    }

    public Matrix(double[][] rowsArray) {
        if (rowsArray.length == 0) {
            throw new IllegalArgumentException("Row count can't be = 0");
        }

        rows = new Vector[rowsArray.length];

        int maxSize = 0;

        for (double[] row : rowsArray) {
            if (row.length > maxSize) {
                maxSize = row.length;
            }
        }

        if (maxSize == 0) {
            throw new IllegalArgumentException("Column count can't be = 0");
        }

        for (int i = 0; i < rowsArray.length; i++) {
            rows[i] = new Vector(maxSize, rowsArray[i]);
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

        rows = new Vector[vectors.length];

        for (int i = 0; i < vectors.length; i++) {
            rows[i] = new Vector(maxSize);
            rows[i].add(vectors[i]);
        }
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return getRow(0).getSize();
    }

    public Vector getRow(int index) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("Index of row = " + index
                    + "; Index of row can't be < 0 or >= " + getRowsCount());
        }

        return new Vector(rows[index]);
    }

    public void setRow(int index, Vector vector) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("Index of row = " + index
                    + "; Index of row can't be < 0 or >= " + getRowsCount());
        }

        if (vector.getSize() != rows[index].getSize()) {
            throw new IllegalArgumentException("Size of the vector = " + vector.getSize()
                    + "; Size of the vector can't be different from the size of the matrix rows; Matrix row size = "
                    + rows[index].getSize());
        }

        rows[index] = new Vector(vector);
    }

    public Vector getColumn(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index of column = " + index
                    + "; Index of column can't be < 0 or >= " + getColumnsCount());
        }

        Vector columnVector = new Vector(getRowsCount());

        for (int i = 0; i < getRowsCount(); i++) {
            columnVector.setComponentByIndex(i, rows[i].getComponentByIndex(index));
        }

        return columnVector;
    }

    public void transpose() {
        Vector[] transposedMatrixRows = new Vector[getColumnsCount()];

        for (int i = 0; i < getColumnsCount(); i++) {
            transposedMatrixRows[i] = getColumn(i);
        }

        rows = new Vector[getColumnsCount()];

        if (rows.length >= 0) System.arraycopy(transposedMatrixRows, 0, rows, 0, rows.length);
    }

    public void multiplyByScalar(double scalar) {
        for (Vector vector : rows) {
            vector.multiplyByScalar(scalar);
        }
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder("{ ");

        for (int i = 0; i < getRowsCount() - 1; i++) {
            resultString.append(rows[i].toString()).append(", ");
        }

        resultString.append(rows[getRowsCount() - 1].toString()).append(" }");

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
        if (getRowsCount() != getColumnsCount()) {
            throw new IllegalArgumentException("Matrix is not square; Row count = " + getRowsCount() + "; Column count = " + getColumnsCount());
        }

        if (getColumnsCount() == 0) {
            throw new IllegalArgumentException("Matrix column size can't be == 0, columnsCount = " + getColumnsCount());
        }

        if (getRowsCount() == 0) {
            throw new IllegalArgumentException("Matrix row size can't be == 0, rowCount = " + getRowsCount());
        }

        return getDeterminantRecursively(getNumbersArray(), rows.length);
    }

    private double[][] getNumbersArray() {
        int maxSize = 0;

        for (Vector vector : rows) {
            if (vector.getSize() > maxSize) {
                maxSize = vector.getSize();
            }
        }

        double[][] numbersArray = new double[rows.length][maxSize];

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].getSize(); j++) {
                numbersArray[i][j] = rows[i].getComponentByIndex(j);
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
        if (getRowsCount() != getColumnsCount()) {
            throw new IllegalArgumentException("Matrix is not square; Row count = " + getRowsCount() + "; Column count = " + getColumnsCount());
        }

        if (getColumnsCount() == 0) {
            throw new IllegalArgumentException("Matrix column size can't be == 0, columnsCount = " + getColumnsCount());
        }

        if (getRowsCount() == 0) {
            throw new IllegalArgumentException("Matrix row size can't be == 0, rowCount = " + getRowsCount());
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.getRow(i));
        }
    }

    public void subtract(Matrix matrix) {
        if (getRowsCount() != getColumnsCount()) {
            throw new IllegalArgumentException("Matrix is not square; Row count = " + getRowsCount() + "; Column count = " + getColumnsCount());
        }

        if (getColumnsCount() == 0) {
            throw new IllegalArgumentException("Matrix column size can't be == 0, columnsCount = " + getColumnsCount());
        }

        if (getRowsCount() == 0) {
            throw new IllegalArgumentException("Matrix row size can't be == 0, rowCount = " + getRowsCount());
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.getRow(i));
        }
    }

    public void multiplyByVector(Vector vector) {
        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Vector size can be = matrix size; vector size = " + vector.getSize());
        }

        Vector resultColumnVector = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            int rowSumNumbers = 0;

            for (int j = 0; j < vector.getSize(); j++) {
                rowSumNumbers += rows[i].getComponentByIndex(j) * vector.getComponentByIndex(j);
            }

            resultColumnVector.setComponentByIndex(i, rowSumNumbers);
        }

        rows = new Vector[]{resultColumnVector};
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Row count matrix 1 != row count matrix 2");
        }

        if (matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Column count of the matrix 1 != column count of the matrix 2");
        }

        Matrix matrixSum = new Matrix(matrix1.getRowsCount(), matrix1.getColumnsCount());

        for (int i = 0; i < matrixSum.getRowsCount(); i++) {
            matrixSum.setRow(i, Vector.getSum(matrix1.getRow(i), matrix2.getRow(i)));
        }

        return matrixSum;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Row count matrix 1 != row count matrix 2");
        }

        if (matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("column count of the matrix 1 != column count of the matrix 2");
        }

        Matrix matrixDifference = new Matrix(matrix1.getRowsCount(), matrix1.getColumnsCount());

        for (int i = 0; i < matrixDifference.getRowsCount(); i++) {
            matrixDifference.setRow(i, Vector.getDifference(matrix1.getRow(i), matrix2.getRow(i)));
        }

        return matrixDifference;
    }

    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Column count matrix 1 != row count matrix 2");
        }

        Matrix matrixMultiply = new Matrix(matrix1.getRowsCount(), matrix2.getColumnsCount());

        for (int i = 0; i < matrix1.getRowsCount(); i++) {
            for (int j = 0; j < matrix2.getColumnsCount(); j++) {
                matrixMultiply.rows[i].setComponentByIndex(j, Vector.getScalarMultiplicationResult(matrix1.getRow(i), matrix2.getColumn(j)));
            }
        }

        return matrixMultiply;
    }
}