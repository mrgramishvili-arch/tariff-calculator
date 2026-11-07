package ru.skillbox.domain;

public record OuterDimensions(Length length, Length width, Length height) {

    public OuterDimensions {
        if (length == null || width == null || height == null) {
            throw new IllegalArgumentException("Dimensions cannot be null");
        }
    }

    public double volumeInCubicMeters() {
        long normLength = roundTo50(length.millimeters());
        long normWidth = roundTo50(width.millimeters());
        long normHeight = roundTo50(height.millimeters());

        double volumeMm3 = (double) normLength * normWidth * normHeight;
        double volumeM3 = volumeMm3 / 1_000_000_000.0;

        return Math.round(volumeM3 * 10000.0) / 10000.0;
    }

    private long roundTo50(long value) {
        return Math.round(value / 50.0) * 50;
    }
}
