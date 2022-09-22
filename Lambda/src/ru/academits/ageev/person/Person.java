package ru.academits.ageev.person;

public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age must be >= 0, age = " + age);
        }

        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
