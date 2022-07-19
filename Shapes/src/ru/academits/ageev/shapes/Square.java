package ru.academits.ageev.shapes;

public class Square implements Shape {
    private final double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double getWight() {
        return side;
    }

    @Override
    public double getHeight() {
        return side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public double getPerimeter() {
        return side * 4;
    }

    @Override
    public String toString() {
        return "Фигура: квадрат" + "\n" +
                "Сторона: " + this.getHeight() + "\n" +
                "Площадь: " + this.getArea() + "\n" +
                "Периметр: " + this.getPerimeter();
    }
}