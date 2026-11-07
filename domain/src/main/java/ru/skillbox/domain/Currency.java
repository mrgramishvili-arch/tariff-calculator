package ru.skillbox.domain;

public record Currency(String code) {
    public Currency {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Currency code cannot be empty");
        }
    }
}
