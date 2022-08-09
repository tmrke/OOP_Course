package ru.academits.ageev.matrix;

import ru.academits.ageev.vector.Vector;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (columnsCount <= 0) {
            throw new IllegalArgumentException("Matrix columns count can't be < 0, columnsCount = " + columnsCount);
        }

        if (rowsCount <= 0) {
            throw new IllegalArgumentException("Matrix rows count can't be < 0, rowsCount = " + rowsCount);
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
            throw new IllegalArgumentException("Rows count can't be = 0");
        }

        rows = new Vector[rowsArray.length];

        int maxSize = 0;

        for (double[] row : rowsArray) {
            if (row.length > maxSize) {
                maxSize = row.length;
            }
        }

        if (maxSize == 0) {
            throw new IllegalArgumentException("Columns count can't be = 0");
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
                    + "; Size of the vector can't be different from the size of the matrix row; Matrix row size = "
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

        rows = transposedMatrixRows;
    }

    public void multiplyByScalar(double scalar) {
        for (Vector vector : rows) {
            vector.multiplyByScalar(scalar);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (int i = 0; i < getRowsCount() - 1; i++) {
            stringBuilder.append(rows[i]).append(", ");
        }

        stringBuilder.append(rows[getRowsCount() - 1]).append("}");

        return stringBuilder.toString();
    }

    private static double[][] getMinor(double[][] coefficientsMatrix, int inputColumnIndex) {
        int sizeCoefficientsMatrix = coefficientsMatrix.length;
        int subArraySize = sizeCoefficientsMatrix - 1;

        double[][] minor = new double[subArraySize][subArraySize];

        for (int i = 1; i < sizeCoefficientsMatrix; i++) {
            int columnIndex = 0;

            for (int j = 0; j < sizeCoefficientsMatrix; j++) {
                if (j == inputColumnIndex) {
                    continue;
                }

                minor[i - 1][columnIndex] = coefficientsMatrix[i][j];
                columnIndex++;
            }
        }

        return minor;
    }

    public double getDeterminant() {
        if (getRowsCount() != getColumnsCount()) {
            throw new UnsupportedOperationException("Matrix is not square; rowsCount = " + getRowsCount() + "; columnsCount = " + getColumnsCount());
        }

        return getDeterminant(getMatrixComponents());
    }

    private double[][] getMatrixComponents() {
        double[][] numbersArray = new double[rows.length][rows.length];

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].getSize(); j++) {
                numbersArray[i][j] = rows[i].getComponentByIndex(j);
            }
        }

        return numbersArray;
    }

    private double getDeterminant(double[][] numbersArray) {
        if (numbersArray.length == 1) {
            return numbersArray[0][0];
        }

        if (numbersArray.length == 2) {
            return numbersArray[0][0] * numbersArray[1][1] - numbersArray[1][0] * numbersArray[0][1];
        }

        double determinant = 0;

        for (int j1 = 0; j1 < numbersArray.length; j1++) {
            double[][] minor = getMinor(numbersArray, j1);
            determinant += Math.pow(-1.0, j1) * numbersArray[0][j1] * getDeterminant(minor);
        }

        return determinant;
    }

    public void add(Matrix matrix) {
        if (getRowsCount() != getColumnsCount()) {
            throw new UnsupportedOperationException("Matrix is not square; rowsCount = " + getRowsCount() + "; columnsCount = " + getColumnsCount());
        }

        if (matrix.getRowsCount() != getRowsCount()) {
            throw new UnsupportedOperationException("Matrix rows count must be = current matrix rows count; matrix rowsCount = " + matrix.getRowsCount()
                    + "; current matrix rowsCount = " + getRowsCount());
        }

        if (matrix.getColumnsCount() != getColumnsCount()) {
            throw new UnsupportedOperationException("Matrix columns count must be = current matrix columnsCount; matrix columnsCount = " + matrix.getRowsCount()
                    + "; current matrix columnsCount = " + getRowsCount());
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.getRow(i));
        }
    }

    public void subtract(Matrix matrix) {
        if (getRowsCount() != getColumnsCount()) {
            throw new UnsupportedOperationException("Matrix is not square; rowsCount = " + getRowsCount() + "; columnsCount = " + getColumnsCount());
        }

        if (matrix.getRowsCount() != getRowsCount()) {
            throw new UnsupportedOperationException("Matrix rows count must be = current matrix rows count; matrix rowsCount = " + matrix.getRowsCount()
                    + "; current matrix rowsCount = " + getRowsCount());
        }

        if (matrix.getColumnsCount() != getColumnsCount()) {
            throw new UnsupportedOperationException("Matrix columns count must be = current matrix columnsCount; matrix columnsCount = " + matrix.getRowsCount()
                    + "; current matrix columnsCount = " + getRowsCount());
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.getRow(i));
        }
    }

    public Vector multiplyByVector(Vector vector) {
        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Vector size must be = matrix size; vector size = " + vector.getSize()
                    + "; current vector size = " + rows.length);
        }

        Vector resultColumnVector = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            resultColumnVector.setComponentByIndex(i, Vector.getScalarMultiplicationResult(rows[i], vector));
        }

        return resultColumnVector;
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Rows count matrix 1 != rows count matrix 2; matrix1 rowsCount = " + matrix1.getRowsCount()
                    + "; matrix2 rowsCount = " + matrix2.getRowsCount());
        }

        if (matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Columns count of the matrix 1 != columns count of the matrix 2; matrix2 columnsCount = "
                    + matrix2.getColumnsCount() + "; matrix2 columnsCount = " + matrix2.getColumnsCount());
        }

        Matrix matricesSum = new Matrix(matrix1);
        matricesSum.add(matrix2);

        return matricesSum;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Rows count matrix 1 != rows count matrix 2; matrix1 rowsCount = " + matrix1.getRowsCount()
                    + "; matrix2 rowsCount = " + matrix2.getRowsCount());
        }

        if (matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Columns count of the matrix 1 != columns count of the matrix 2; matrix2 columnsCount = "
                    + matrix2.getColumnsCount() + "; matrix2 columnsCount = " + matrix2.getColumnsCount());
        }

        Matrix matricesDifference = new Matrix(matrix1);
        matricesDifference.subtract(matrix2);

        return matricesDifference;
    }

    public static Matrix getMultiplicationResult(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Columns count matrix 1 != rows count matrix 2");
        }

        Matrix matricesMultiplicationResult = new Matrix(matrix1.getRowsCount(), matrix2.getColumnsCount());

        for (int i = 0; i < matrix1.getRowsCount(); i++) {
            for (int j = 0; j < matrix2.getColumnsCount(); j++) {
                matricesMultiplicationResult.rows[i].setComponentByIndex(j, Vector.getScalarMultiplicationResult(matrix1.getRow(i), matrix2.getColumn(j)));
            }
        }

        return matricesMultiplicationResult;
    }
}