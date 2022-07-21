package ru.academits.ageev.shapes;

public record Square(double side) implements Shape {

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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Square s = (Square) o;

        return this.side == s.side;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        return prime * hash + Double.hashCode(this.side);
    }
}