package range_main;

import ru.academits.ageev.Range;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Range range = new Range(5, 20);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Диапазон от " + range.getFrom() + " до " + range.getTo());
        System.out.println("Длина диапазона = " + range.getLength());

        System.out.println("Введите число для проверки вхождения в диапазон:");
        double number = scanner.nextDouble();

        if (range.isInside(number)) {
            System.out.println("Число в диапазоне");
        } else {
            System.out.println("Число не в диапазоне");
        }

        System.out.println("Измените начало диапазона:");
        range.setFrom(scanner.nextDouble());

        System.out.println("Измените конец диапазона:");
        range.setTo(scanner.nextDouble());

        System.out.println("Новый диапазон от " + range.getFrom() + " до " + range.getTo());
    }
}