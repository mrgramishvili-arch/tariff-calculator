package ru.skillbox.domain;

public record Pack(Weight weight, OuterDimensions dimensions) {
    public Pack {
        if (weight == null) {
            throw new IllegalArgumentException("Weight cannot be null");
        }
    }
}
