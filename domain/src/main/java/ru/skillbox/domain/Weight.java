package ru.skillbox.domain;

public record Weight(long grams) {
    public static final long MAX_WEIGHT_GRAMS = 150_000;
    public static final long MIN_WEIGHT_GRAMS = 0;

    public Weight {
        if (grams < MIN_WEIGHT_GRAMS) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        if (grams > MAX_WEIGHT_GRAMS) {
            throw new IllegalArgumentException("Weight cannot exceed 150 kg");
        }
    }

    public static Weight fromGrams(long grams) {
        return new Weight(grams);
    }
}
