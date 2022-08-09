package ru.academits.ageev.matrix_main;

import ru.academits.ageev.matrix.Matrix;
import ru.academits.ageev.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector vector1 = new Vector(new double[]{3, 10, 5});
        Vector vector2 = new Vector(4);
        Vector vector3 = new Vector(new double[]{1, 2, 3, 4, 5});
        Vector vector4 = new Vector(new double[]{3, 10, 5});

        Vector[] vectors = {vector1, vector2, vector3};

        Matrix matrix1 = new Matrix(vectors);
        Matrix matrix2 = new Matrix(3, 3);
        Matrix matrix3 = new Matrix(matrix2);
        Matrix matrix4 = new Matrix(new double[][]{{1, 2, 3, 4, 5}, {4, 5, 6, 7, 8}, {7, 8, 9, 10, 11}, {7, 8, 9, 10, 11}, {7, 8, 9, 10, 11}});
        Matrix matrix5 = new Matrix(new double[][]{{1, 0, 2}, {-1, 3, 4}, {-2, 1, 5}});
        Matrix matrix6 = new Matrix(new double[][]{{1, 1, 1}, {-1, -1, -1}, {0, 0, 0}});
        Matrix matrix7 = new Matrix(new double[][]{{3, -1, 2}, {4, 2, 0}, {-5, 6, 1}});
        Matrix matrix8 = new Matrix(new double[][]{{8, 1}, {7, 2}, {2, -3}});

        System.out.println("Вектор-строка матрицы 3 по индексу 2: " + matrix3.getRow(2));
        matrix3.setRow(2, matrix5.getRow(1));
        System.out.println("Новый вектор-строка матрицы 3 по индексу 3: " + matrix3.getRow(2));

        System.out.println("Матрица 4: " + matrix4);
        matrix4.transpose();
        System.out.println("Транспонированная матрица 4: " + matrix4);

        matrix1.multiplyByScalar(10);
        System.out.println("Матрица 1, умноженная на скаляр \"10\": " + matrix1);

        System.out.println("Определитель матрицы 5 = " + matrix5.getDeterminant());

        matrix5.add(matrix6);
        System.out.println("Матрица 5 + матрица 6 = " + matrix5);

        matrix5.subtract(matrix6);
        System.out.println("Матрица 5 - матрица 6 = " + matrix5);

        System.out.println("Результат умножения матрицы 6 на вектор 4 = " + matrix6.multiplyByVector(vector4));

        System.out.println("Результат сложения матрицы 5  с матрицей 7 = " + Matrix.getSum(matrix5, matrix7));
        System.out.println("Результат вычитания из матрицы 5  матрицы 7= " + Matrix.getDifference(matrix5, matrix7));
        System.out.println("Результат умножения матрицы 7 на матрицу 8 = " + Matrix.getMultiplicationResult(matrix7, matrix8));
    }
}