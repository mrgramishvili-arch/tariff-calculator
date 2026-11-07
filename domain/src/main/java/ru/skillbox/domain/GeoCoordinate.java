package ru.skillbox.domain;

public record GeoCoordinate(double latitude, double longitude) {
    public GeoCoordinate {
        if (latitude < 45 || latitude > 65) {
            throw new IllegalArgumentException("Latitude must be between 45 and 65");
        }
        if (longitude < 30 || longitude > 96) {
            throw new IllegalArgumentException("Longitude must be between 30 and 96");
        }
    }
}
