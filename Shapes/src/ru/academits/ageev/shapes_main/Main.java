package ru.academits.ageev.shapes_main;

import ru.academits.ageev.shapes_comparators.AreaComparator;
import ru.academits.ageev.shapes_comparators.PerimeterComparator;
import ru.academits.ageev.shapes.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Square(10),
                new Square(10),
                new Triangle(0, 0, 0, 300, 400, 0),
                new Triangle(5, 0, 0, 5, 8, 0),
                new Rectangle(4, 6),
                new Rectangle(4, 7),
                new Circle(10),
                new Circle(10)
        };

        if (shapes[0].equals(shapes[1])) {
            System.out.println("Фигуры равны");
        } else {
            System.out.println("Фигуры не равны");
        }

        System.out.println("Максимальная по площади фигура: " + getMaxAreaShape(shapes));
        System.out.println("Вторая по величине периметра фигура: " + getSecondLargestPerimeterShape(shapes));
    }

    public static Shape getMaxAreaShape(Shape[] shapes) {
        if (shapes.length == 0) {
            System.out.println("В массиве нет фигур");
            return null;
        }

        Arrays.sort(shapes, new AreaComparator());

        return shapes[shapes.length - 1];
    }

    public static Shape getSecondLargestPerimeterShape(Shape[] shapes) {
        if (shapes.length < 2) {
            System.out.println("В массиве нет или недостаточно фигур");
            return null;
        }

        Arrays.sort(shapes, new PerimeterComparator());

        return shapes[shapes.length - 2];
    }
}