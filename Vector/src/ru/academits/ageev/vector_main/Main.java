package ru.academits.ageev.vector_main;

import ru.academits.ageev.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector vector1 = new Vector(new double[]{1, 2, 3, 4, 5, 6});
        Vector vector2 = new Vector(new double[]{1, 2, 3, 4, 5, 6});

        System.out.println("Размер vector1 = " + vector1.getSize());
        vector1.setSize(3);
        System.out.println("Новый размер vector1 = " + vector1.getSize());

        vector2.subtract(vector1);
        System.out.println("vector2 = " + vector2);

        vector1.add(vector2);
        System.out.println("vector1 = " + vector1);

        vector1.reverse();
        System.out.println("vector1 = " + vector1);

        vector1.multiply(-10);
        System.out.println("vector1 = " + vector1);

        System.out.println("component by index 5 from vector1 = " + vector1.getComponentByIndex(5));
        vector1.setComponentByIndex(5, 100);
        System.out.println("component by index 5 from vector1 = " + vector1.getComponentByIndex(5));

        Vector vector3 = Vector.add(vector1, new Vector(8, new double[]{5, 10, 15, 20}));
        System.out.println("vector3 = " + vector3);

        Vector vector4 = Vector.subtract(new Vector(4), new Vector(vector3));
        System.out.println("vector4 = " + vector4);

        Vector vector5 = Vector.multiply(vector4, 10);
        System.out.println("vector5 = " + vector5);
    }
}
