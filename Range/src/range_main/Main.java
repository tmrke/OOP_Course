package range_main;

import ru.academits.ageev.Range;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Range range1 = new Range(0, 30);
        Range range2 = new Range(10, 20);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Диапазон 1 от " + range1.getFrom() + " до " + range1.getTo());
        System.out.println("Диапазон 2 от " + range2.getFrom() + " до " + range2.getTo());
        System.out.println("Длина диапазона 1 = " + range1.getLength());
        System.out.println("Длина диапазона 2 = " + range2.getLength());

        System.out.println("Введите число для проверки вхождения в диапазон:");
        double number = scanner.nextDouble();

        if (range1.isInside(number)) {
            System.out.println("Число в диапазоне");
        } else {
            System.out.println("Число не в диапазоне");
        }

        Range intersectionRange = range1.getIntersectionInterval(range2);
        System.out.println("Результат пересечения диапазонов: " + intersectionRange);

        Range[] mergingRanges = range1.getMergingIntervals(range2);
        System.out.println("Результат объединения диапазонов: " + mergingRanges[0] + ", " + mergingRanges[1]);

        Range[] differenceRanges = range1.getDifferenceIntervals(range2);
        if (differenceRanges == null) {
            System.out.println("Результат разности диапазонов: = " + null);
        } else {
            System.out.println("Результат разности диапазонов: " + differenceRanges[0] + ", " + differenceRanges[1]);
        }
    }
}