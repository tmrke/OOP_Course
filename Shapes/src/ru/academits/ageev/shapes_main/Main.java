package ru.academits.ageev.shapes_main;

import ru.academits.ageev.comparator.AreaComparator;
import ru.academits.ageev.shapes.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[8];

        shapes[0] = new Square(5);
        shapes[1] = new Square(10);
        shapes[2] = new Triangle(0, 0, 0, 3, 4, 0);
        shapes[3] = new Triangle(0, 0, 0, 6, 8, 0);
        shapes[4] = new Rectangle(4, 6);
        shapes[5] = new Rectangle(8, 12);
        shapes[6] = new Circle(5);
        shapes[7] = new Circle(10);

        System.out.println(getMaxAreaShape(shapes));
        System.out.println(getSecondLargestPerimeterShape(shapes));
    }

    public static Shape getMaxAreaShape(Shape[] shapes) {
        AreaComparator areaComparator = new AreaComparator();
        Arrays.sort(shapes, areaComparator);

        return shapes[7];
    }

    public static Shape getSecondLargestPerimeterShape(Shape[] shapes) {
        AreaComparator areaComparator = new AreaComparator();
        Arrays.sort(shapes, areaComparator);

        return shapes[6];
    }
}
