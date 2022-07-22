package ru.academits.ageev.vector_main;

import ru.academits.ageev.vector.Vector;

public class Main {
    public static void main(String[] args) {

        Vector vector1 = new Vector(new double[]{1, 2, 3, 4, 5, 6, 4, 5});
        Vector vector2 = new Vector(new double[]{-1, 3, 5, 5, 5, 5, 0, 10});

        Vector vector3 = Vector.multiply(vector1, 2);
        System.out.println(vector3);
    }
}
