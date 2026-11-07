package ru.skillbox.domain;

import java.util.List;

public record Shipment(
        List<Pack> packs,
        Currency currency,
        GeoCoordinate departure,
        GeoCoordinate destination
) {
    public Shipment {
        if (packs == null || packs.isEmpty()) {
            throw new IllegalArgumentException("Packs cannot be empty");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
    }
}
