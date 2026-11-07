package ru.skillbox.domain;

public record Length(long millimeters) {
    public static final long MAX_DIMENSION = 1500;
    public static final long MIN_DIMENSION = 0;

    public Length {
        if (millimeters < MIN_DIMENSION) {
            throw new IllegalArgumentException("Length cannot be negative");
        }
        if (millimeters > MAX_DIMENSION) {
            throw new IllegalArgumentException("Length cannot exceed 1500 mm");
        }
    }

    public static Length fromMillimeter(long mm) {
        return new Length(mm);
    }

    public boolean isLongerThan(Length other) {
        return this.millimeters > other.millimeters;
    }
}
