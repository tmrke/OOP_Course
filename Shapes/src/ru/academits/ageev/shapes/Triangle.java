package ru.academits.ageev.shapes;

public class Triangle implements Shape {
    private final double x1;
    private final double x2;
    private final double x3;

    private final double y1;
    private final double y2;
    private final double y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;

        this.x2 = x2;
        this.y2 = y2;

        this.x3 = x3;
        this.y3 = y3;
    }

    @Override
    public double getWight() {
        double maxX = Math.max(Math.max(x1, x2), x3);
        double minX = Math.min(Math.min(x1, x2), x3);

        return maxX - minX;
    }

    @Override
    public double getHeight() {
        double maxY = Math.max(Math.max(y1, y2), y3);
        double minY = Math.min(Math.min(y1, y2), y3);

        return maxY - minY;
    }

    @Override
    public double getArea() {
        double side1 = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        double side2 = Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));
        double side3 = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));

        double triangleHalfPerimeter = (side1 + side2 + side3) / 2;

        return Math.sqrt(triangleHalfPerimeter * (triangleHalfPerimeter - side1) *
                (triangleHalfPerimeter - side2) * (triangleHalfPerimeter - side3));
    }

    @Override
    public double getPerimeter() {
        double side1 = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        double side2 = Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));
        double side3 = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));

        return side1 + side2 + side3;
    }

    @Override
    public String toString() {
        return "Фигура: треугольник" + "\n" +
                "Ширина: " + this.getWight() + "\n" +
                "Высота: " + this.getHeight() + "\n" +
                "Площадь: " + this.getArea() + "\n" +
                "Периметр: " + this.getPerimeter();
    }
}