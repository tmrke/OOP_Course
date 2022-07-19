package ru.academits.ageev.shapes;

public class Circle implements Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getWight() {
        return radius * 2;
    }

    @Override
    public double getHeight() {
        return radius * 2;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return "Фигура: круг" + "\n" +
                "Радиус: " + radius + "\n" +
                "Диаметр: " + this.getHeight() + "\n" +
                "Площадь: " + this.getArea() + "\n" +
                "Длина окружности: " + this.getPerimeter();
    }
}
