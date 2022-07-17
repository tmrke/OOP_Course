package ru.academits.ageev.range;

public class Range {
    private final double from;
    private final double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return number >= from && number <= to;
    }

    public Range getIntersectionInterval(Range range2) {
        if (this.to <= range2.from || this.from >= range2.to) {
            return null;
        }

        double newFrom = Math.max(this.from, range2.from);
        double newTo = Math.min(this.to, range2.to);

        return new Range(newFrom, newTo);
    }

    public Range[] getMergingIntervals(Range range2) {
        Range newRange1;
        Range newRange2;

        if (this.to < range2.from || this.from > range2.to) {
            newRange1 = this;
            newRange2 = range2;
        } else {
            double newFrom1 = Math.min(this.from, range2.from);
            double newTo1 = Math.max(this.to, range2.to);

            newRange1 = new Range(newFrom1, newTo1);
            newRange2 = null;
        }

        return new Range[]{newRange1, newRange2};
    }

    public Range[] getDifferenceIntervals(Range range2) {
        if (this.from >= range2.from && this.to <= range2.to) {
            return null;
        }

        if (this.from < range2.from && this.to > range2.to) {
            Range newRange1 = new Range(this.from, range2.from);
            Range newRange2 = new Range(range2.to, this.to);

            return new Range[]{newRange1, newRange2};
        }

        Range newRange1;

        if (this.from > range2.from && this.to > range2.to && range2.to > this.from) {
            newRange1 = new Range(range2.to, this.to);
        } else {
            newRange1 = new Range(this.from, range2.from);
        }

        return new Range[]{newRange1, null};
    }

    @Override
    public String toString() {
        return "от " + from + " до " + to;
    }
}