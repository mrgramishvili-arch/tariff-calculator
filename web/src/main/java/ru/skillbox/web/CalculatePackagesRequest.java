package ru.skillbox.web;

import java.util.List;

public record CalculatePackagesRequest(
        List<PackageDto> packages,
        String currencyCode,
        CoordinateDto departure,
        CoordinateDto destination
) {
    public record PackageDto(
            Long weight,
            Long length,
            Long width,
            Long height
    ) {}

    public record CoordinateDto(
            Double latitude,
            Double longitude
    ) {}
}
