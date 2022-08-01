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

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public double getX3() {
        return x3;
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

    private static double getSideLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    @Override
    public double getArea() {
        double side1Length = getSideLength(x1, y1, x2, y2);
        double side2Length = getSideLength(x2, y2, x3, y3);
        double side3Length = getSideLength(x3, y3, x1, y1);

        double triangleHalfPerimeter = (side1Length + side2Length + side3Length) / 2;

        return Math.sqrt(triangleHalfPerimeter * (triangleHalfPerimeter - side1Length) *
                (triangleHalfPerimeter - side2Length) * (triangleHalfPerimeter - side3Length));
    }

    @Override
    public double getPerimeter() {
        double side1Length = getSideLength(x1, y1, x2, y2);
        double side2Length = getSideLength(x2, y2, x3, y3);
        double side3Length = getSideLength(x3, y3, x1, y1);

        return side1Length + side2Length + side3Length;
    }

    @Override
    public String toString() {
        return "Фигура: треугольник; координаты: ("
                + x1 + "; " + y1 + "), (" + x2 + "; " + y2 + "), (" + x3 + "; " + y3 +
                "); площадь: " + getArea() +
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

        return x1 == t.x1 && x2 == t.x2 && x3 == t.x3
                && y1 == t.y1 && y2 == t.y2 && y3 == t.y3;
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