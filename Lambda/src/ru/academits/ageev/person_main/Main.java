package ru.academits.ageev.person_main;

import ru.academits.ageev.person.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Main {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Иван", 10),
                new Person("Петр", 15),
                new Person("Елена", 20),
                new Person("Ульяна", 30),
                new Person("Иван", 150)
        );

        System.out.println("Исходный список:");
        System.out.println(persons);

        List<String> uniqueNames = persons
                .stream()
                .map(Person::name)
                .distinct()
                .toList();

        System.out.println("Список уникальных имен: " + uniqueNames);

        String uniqueNamesString = uniqueNames
                .stream()
                .collect(Collectors.joining(", ", "Уникальные имена: ", "."));

        System.out.println(uniqueNamesString);

        List<Person> less18AgePersons = persons
                .stream()
                .filter(p -> p.age() < 18)
                .toList();

        System.out.println("Список людей младше 18 лет: " + less18AgePersons);

        OptionalDouble optionalDoubleLess18AgePersonsAverageAge = less18AgePersons
                .stream()
                .mapToInt(Person::age)
                .average();

        double less18AgePersonsAverageAge = optionalDoubleLess18AgePersonsAverageAge.getAsDouble();
        System.out.println("Средний возраст людей младше 18 лет = " + less18AgePersonsAverageAge);

        Map<String, Double> mapByAverageAge = persons
                .stream()
                .collect(Collectors.groupingBy(Person::name, Collectors.averagingDouble(Person::age)));

        System.out.println("Map<\"Имена\", \"Средний возраст\"> : " + mapByAverageAge);

        String from20to45yearsPersons = persons
                .stream()
                .filter(p -> p.age() >= 20 && p.age() <= 45)
                .sorted(Comparator.comparingInt(Person::age).reversed())
                .map(Person::name)
                .collect(Collectors.joining(", ", "Люди возрастом от 20 и до 45 лет: ", "."));

        System.out.println(from20to45yearsPersons);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Сколько элементов нужно вычислить?");
        int elementsCount = scanner.nextInt();

        DoubleStream squareRoots = DoubleStream.iterate(0, x -> x + 1).map(Math::sqrt).limit(elementsCount);
        squareRoots.forEach(System.out::println);
    }
}