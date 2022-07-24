package ru.academits.ageev.range_main;

import ru.academits.ageev.range.Range;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Range range1 = new Range(10, 40);
        Range range2 = new Range(20, 30);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Диапазон 1: " + range1.getFrom() + " до " + range1.getTo());
        System.out.println("Диапазон 2 от " + range2.getFrom() + " до " + range2.getTo());

        System.out.println("Длина диапазона 1 = " + range1.getLength());
        System.out.println("Длина диапазона 2 = " + range2.getLength());

        System.out.println("Измените начало первого диапазона:");
        range1.setFrom(scanner.nextDouble());

        System.out.println("Измените конец первого диапазона:");
        range1.setTo(scanner.nextDouble());

        System.out.println("Новый диапазон от " + range1.getFrom() + " до " + range1.getTo());

        System.out.println("Введите число для проверки вхождения в диапазон:");
        double number = scanner.nextDouble();

        if (range1.isInside(number)) {
            System.out.println("Число в диапазоне");
        } else {
            System.out.println("Число не в диапазоне");
        }

        Range intersectionRange = range1.getIntersection(range2);

        if (intersectionRange == null) {
            System.out.println("Результат пересечения диапазонов: " + Arrays.toString(new Range[0]));
        } else {
            System.out.println("Результат пересечения диапазонов: " + Arrays.toString(new Range[]{intersectionRange}));
        }

        Range[] unionRanges = range1.getUnion(range2);
        System.out.println("Результат объединения диапазонов: " +
                Arrays.toString(Objects.requireNonNullElseGet(unionRanges, () -> new Range[0])));

        Range[] differenceRanges = range1.getDifference(range2);
        System.out.println("Результат разности диапазонов: " +
                Arrays.toString(Objects.requireNonNullElseGet(differenceRanges, () -> new Range[0])));
    }
}