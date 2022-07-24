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

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getX3() {
        return x3;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public double getY3() {
        return y3;
    }

    @Override
    public double getWidth() {
        return Math.max(Math.max(x1, x2), x3) - Math.min(Math.min(x1, x2), x3);
    }

    @Override
    public double getHeight() {
        return Math.max(Math.max(y1, y2), y3) - Math.min(Math.min(y1, y2), y3);
    }

    public double getSideLength(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    @Override
    public double getArea() {
        double sideLength1 = getSideLength(x1, x2, y1, y2);
        double sideLength2 = getSideLength(x2, x3, y2, y3);
        double sideLength3 = getSideLength(x3, x1, y3, y1);

        double triangleHalfPerimeter = (sideLength1 + sideLength2 + sideLength3) / 2;

        return Math.sqrt(triangleHalfPerimeter * (triangleHalfPerimeter - sideLength1) *
                (triangleHalfPerimeter - sideLength2) * (triangleHalfPerimeter - sideLength3));
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
        return "Фигура: треугольник; координаты точки 1: " + x1 + ", " + y1 + "; координаты точки 2: " +
                +x2 + ", " + y2 + "; координаты точки 3: " +
                +x3 + ", " + y3 +
                "; площадь: " + getArea() +
                "; периметр: " + getPerimeter();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Triangle t = (Triangle) o;

        return x1 == t.x1 && x2 == t.x2 && x3 == t.x3 &&
                y1 == t.y1 && y2 == t.y2 && y3 == t.y3;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Double.hashCode(x1);
        hash = prime * hash + Double.hashCode(x2);
        hash = prime * hash + Double.hashCode(x3);
        hash = prime * hash + Double.hashCode(y1);
        hash = prime * hash + Double.hashCode(y2);
        hash = prime * hash + Double.hashCode(y3);

        return hash;
    }
}