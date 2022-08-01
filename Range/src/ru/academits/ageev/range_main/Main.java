package ru.academits.ageev.range_main;

import ru.academits.ageev.range.Range;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Range range1 = new Range(3, 5);
        Range range2 = new Range(1, 3);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Диапазон 1: " + range1);
        System.out.println("Диапазон 2: " + range2);

        System.out.println("Длина диапазона 1 = " + range1.getLength());
        System.out.println("Длина диапазона 2 = " + range2.getLength());

        System.out.println("Измените начало первого диапазона:");
        range1.setFrom(scanner.nextDouble());

        System.out.println("Измените конец первого диапазона:");
        range1.setTo(scanner.nextDouble());

        System.out.println("Новый диапазон 1: " + range1);

        System.out.println("Введите число для проверки вхождения в диапазон:");
        double number = scanner.nextDouble();

        if (range1.isInside(number)) {
            System.out.println("Число в диапазоне");
        } else {
            System.out.println("Число не в диапазоне");
        }

        Range intersectionRange = range1.getIntersection(range2);

        System.out.println("Результат пересечения диапазонов: " + Objects.requireNonNullElseGet(intersectionRange,
                () -> Arrays.toString(new Range[0])));

        Range[] unionRanges = range1.getUnion(range2);
        System.out.println("Результат объединения диапазонов: " + Arrays.toString(unionRanges));

        Range[] differenceRanges = range1.getDifference(range2);
        System.out.println("Результат разности диапазонов: " + Arrays.toString(differenceRanges));
    }
}