package ru.skillbox.web;

import java.math.BigDecimal;

public record CalculatePackagesResponse(
        BigDecimal totalPrice,
        BigDecimal minimalPrice,
        String currencyCode
) {
}
