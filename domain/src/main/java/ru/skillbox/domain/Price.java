package ru.skillbox.domain;

import java.math.BigDecimal;

public record Price(BigDecimal amount, Currency currency) {
    public Price {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
    }

    public Price max(Price other) {
        if (this.amount.compareTo(other.amount) >= 0) {
            return this;
        }
        return other;
    }
}
