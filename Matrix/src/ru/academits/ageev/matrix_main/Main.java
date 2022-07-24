package ru.academits.ageev.matrix_main;

import ru.academits.ageev.matrix.Matrix;
import ru.academits.ageev.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector vector1 = new Vector(new double[]{3, 4, 5, 6, 7});
        Vector vector2 = new Vector(4);
        Vector vector3 = new Vector(new double[]{1, 2, 3, 4, 5, 6, 7});

        Vector[] vectors = {vector1, vector2, vector3};

        Matrix matrix1 = new Matrix(vectors);
        Matrix matrix2 = new Matrix(3, 5);
        Matrix matrix3 = new Matrix(matrix2);
        Matrix matrix4 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});


        System.out.println("Вектор-строка по индексу 2:" + matrix1.getVectorRow(2));
        matrix1.setVectorRow(2, matrix3.getVectorRow(2));
        System.out.println("Новый вектор-строка по индексу 2:" + matrix1.getVectorRow(2));

        System.out.println("Матрица 4: " + matrix4);
        matrix4.transpose();
        System.out.println("Транспонированная матрица 4: " + matrix4);

        matrix1.multiplyByScalar(10);
        System.out.println("Матрица 1, умноженная на скаляр \"10\": " + matrix1);


    }
}
