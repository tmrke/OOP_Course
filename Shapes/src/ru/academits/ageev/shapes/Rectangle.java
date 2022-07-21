package ru.academits.ageev.shapes;

public record Rectangle(double weight, double height) implements Shape {

    @Override
    public double getWight() {
        return weight;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getArea() {
        return height * getWight();
    }

    @Override
    public double getPerimeter() {
        return (height + getWight()) * 2;
    }

    @Override
    public String toString() {
        return "Фигура: прямоугольник" + "\n" +
                "Ширина: " + this.getWight() + "\n" +
                "Высота: " + this.getHeight() + "\n" +
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

        Rectangle r = (Rectangle) o;

        return this.height == r.height && this.weight == r.weight;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Double.hashCode(height);
        hash = prime * hash + Double.hashCode(getWight());
        return hash;
    }
}
