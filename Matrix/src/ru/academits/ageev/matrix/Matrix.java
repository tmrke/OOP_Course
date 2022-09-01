package ru.academits.ageev.matrix;

import ru.academits.ageev.vector.Vector;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (columnsCount <= 0) {
            throw new IllegalArgumentException("Matrix columns count can't be <= 0, columnsCount = " + columnsCount);
        }

        if (rowsCount <= 0) {
            throw new IllegalArgumentException("Matrix rows count can't be <= 0, rowsCount = " + rowsCount);
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < matrix.rows.length; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] coefficients) {
        if (coefficients.length == 0) {
            throw new IllegalArgumentException("Rows count can't be = 0");
        }

        rows = new Vector[coefficients.length];

        int maxSize = 0;

        for (double[] row : coefficients) {
            if (row.length > maxSize) {
                maxSize = row.length;
            }
        }

        if (maxSize == 0) {
            throw new IllegalArgumentException("Columns count can't be = 0");
        }

        for (int i = 0; i < coefficients.length; i++) {
            rows[i] = new Vector(maxSize, coefficients[i]);
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
        return rows[0].getSize();
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

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Size of the vector = " + vector.getSize()
                    + "; Size of the vector can't be different from the size of the matrix row; Matrix row size = " + getColumnsCount());
        }

        rows[index] = new Vector(vector);
    }

    public Vector getColumn(int index) {
        if (index < 0 || index >= getColumnsCount()) {
            throw new IndexOutOfBoundsException("Index of column = " + index
                    + "; Index of column can't be < 0 or can't be >= " + getColumnsCount());
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

    private static double[][] getMinor(double[][] matrixCoefficients, int discardedColumnIndex) {
        int matrixCoefficientsSize = matrixCoefficients.length;
        int minorSize = matrixCoefficientsSize - 1;

        double[][] minor = new double[minorSize][minorSize];

        for (int i = 1; i < matrixCoefficientsSize; i++) {
            int columnIndex = 0;

            for (int j = 0; j < matrixCoefficientsSize; j++) {
                if (j == discardedColumnIndex) {
                    continue;
                }

                minor[i - 1][columnIndex] = matrixCoefficients[i][j];
                columnIndex++;
            }
        }

        return minor;
    }

    public double getDeterminant() {
        if (getRowsCount() != getColumnsCount()) {
            throw new UnsupportedOperationException("Matrix is not square; rowsCount = " + getRowsCount()
                    + "; columnsCount = " + getColumnsCount());
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

    private static double getDeterminant(double[][] numbersArray) {
        if (numbersArray.length == 1) {
            return numbersArray[0][0];
        }

        if (numbersArray.length == 2) {
            return numbersArray[0][0] * numbersArray[1][1] - numbersArray[1][0] * numbersArray[0][1];
        }

        double determinant = 0;

        for (int j = 0; j < numbersArray.length; j++) {
            double[][] minor = getMinor(numbersArray, j);
            determinant += Math.pow(-1.0, j) * numbersArray[0][j] * getDeterminant(minor);
        }

        return determinant;
    }

    public void add(Matrix matrix) {
        checkEquality(this, matrix);

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        checkEquality(this, matrix);

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    public Vector multiplyByVector(Vector vector) {
        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Vector size must be = matrix columns count; vector size = " + vector.getSize()
                    + "; matrix columns count = " + getColumnsCount());
        }

        Vector resultColumnVector = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            resultColumnVector.setComponentByIndex(i, Vector.getScalarProduct(rows[i], vector));
        }

        return resultColumnVector;
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        checkEquality(matrix1, matrix2);

        Matrix matricesSum = new Matrix(matrix1);
        matricesSum.add(matrix2);

        return matricesSum;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        checkEquality(matrix1, matrix2);

        Matrix matricesDifference = new Matrix(matrix1);
        matricesDifference.subtract(matrix2);

        return matricesDifference;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Matrix1 columns count != matrix2 rows count; matrix1 columns count = " + matrix1.getColumnsCount()
                    + ";  matrix2 rows count = " + matrix2.getRowsCount());
        }

        Matrix matricesProduct = new Matrix(matrix1.getRowsCount(), matrix2.getColumnsCount());

        for (int i = 0; i < matrix1.getRowsCount(); i++) {
            for (int j = 0; j < matrix2.getColumnsCount(); j++) {
                matricesProduct.rows[i].setComponentByIndex(j, Vector.getScalarProduct(matrix1.rows[i], matrix2.getColumn(j)));
            }
        }

        return matricesProduct;
    }

    private static void checkEquality(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Matrix1 rows count must be = matrix2 rows count; matrix1 rows count = " + matrix1.getRowsCount()
                    + "; matrix2 rows count = " + matrix2.getRowsCount());
        }

        if (matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Matrix1 columns count must be = matrix2 columns count; matrix1 columns count = " + matrix1.getColumnsCount()
                    + "; matrix2 columns count = " + matrix2.getColumnsCount());
        }
    }
}