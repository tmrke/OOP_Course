package ru.academits.ageev.person_main;

import ru.academits.ageev.person.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Main {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Иван", 110),
                new Person("Петр", 115),
                new Person("Елена", 20),
                new Person("Ульяна", 30),
                new Person("Иван", 150)
        );

        System.out.println("Исходный список:");
        System.out.println(persons);

        List<String> uniqueNames = persons.stream()
                .map(Person::getName)
                .distinct()
                .toList();

        System.out.println("Список уникальных имен: " + uniqueNames);

        String uniqueNamesString = uniqueNames.stream()
                .collect(Collectors.joining(", ", "Уникальные имена: ", "."));

        System.out.println(uniqueNamesString);

        List<Person> less18AgePersons = persons.stream()
                .filter(p -> p.getAge() < 18)
                .toList();

        System.out.println("Список людей младше 18 лет: " + less18AgePersons);

        OptionalDouble optionalDoubleLess18AgePersonsAverageAge = less18AgePersons.stream()
                .mapToInt(Person::getAge)
                .average();

        if (optionalDoubleLess18AgePersonsAverageAge.isPresent()) {
            double less18AgePersonsAverageAge = optionalDoubleLess18AgePersonsAverageAge.getAsDouble();
            System.out.println("Средний возраст людей младше 18 лет = " + less18AgePersonsAverageAge);
        }

        Map<String, Double> averageAgesByName = persons.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingInt(Person::getAge)));

        System.out.println("Map<\"Имена\", \"Средний возраст\"> : " + averageAgesByName);

        String from20to45yearsPersonsString = persons.stream()
                .filter(p -> p.getAge() >= 20 && p.getAge() <= 45)
                .sorted(Comparator.comparingInt(Person::getAge).reversed())
                .map(Person::getName)
                .collect(Collectors.joining(", ", "Люди возрастом от 20 и до 45 лет: ", "."));

        System.out.println(from20to45yearsPersonsString);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Сколько элементов нужно вычислить?");
        int elementsCount = scanner.nextInt();

        DoubleStream squareRoots = DoubleStream.iterate(0, x -> x + 1).map(Math::sqrt).limit(elementsCount);
        squareRoots.forEach(System.out::println);
    }
}