package ru.skillbox.usecase;

import ru.skillbox.domain.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateUseCase {

    // Конфигурация (по ТЗ должна быть вынесена, но для простоты пока тут)
    private static final BigDecimal PRICE_PER_GRAM = new BigDecimal("0.36"); // Примерная ставка за грамм
    private static final BigDecimal MINIMAL_PRICE = new BigDecimal("500.00");
    private static final BigDecimal PRICE_PER_CUBIC_METER = new BigDecimal("1000.00"); // Ставка за объем
    private static final double MIN_DISTANCE_KM = 450.0;

    public Price calc(Shipment shipment) {
        // 1. Базовая стоимость (максимум из веса и объема)
        Price byWeight = calculateByWeight(shipment);
        Price byVolume = calculateByVolume(shipment);
        Price basePrice = byWeight.max(byVolume);

        // 2. Если нет координат, возвращаем базовую стоимость
        if (shipment.departure() == null || shipment.destination() == null) {
            return basePrice;
        }

        // 3. Расчет с учетом расстояния
        double distance = DistanceCalculator.calculate(shipment.departure(), shipment.destination());
        double effectiveDistance = Math.max(distance, MIN_DISTANCE_KM);

        // Множитель расстояния
        BigDecimal multiplier = BigDecimal.valueOf(effectiveDistance / MIN_DISTANCE_KM);

        // Итоговая цена = Базовая цена * Множитель (округляем вверх до копеек)
        BigDecimal finalAmount = basePrice.amount()
                .multiply(multiplier)
                .setScale(2, RoundingMode.CEILING);

        return new Price(finalAmount, shipment.currency());
    }

    private Price calculateByWeight(Shipment shipment) {
        long totalGrams = shipment.packs().stream()
                .mapToLong(pack -> pack.weight().grams())
                .sum();

        BigDecimal totalAmount = BigDecimal.valueOf(totalGrams).multiply(PRICE_PER_GRAM);

        // Проверка на минимальную стоимость
        if (totalAmount.compareTo(MINIMAL_PRICE) < 0) {
            totalAmount = MINIMAL_PRICE;
        }

        return new Price(totalAmount.setScale(2, RoundingMode.HALF_UP), shipment.currency());
    }

    private Price calculateByVolume(Shipment shipment) {
        double totalVolume = shipment.packs().stream()
                .filter(pack -> pack.dimensions() != null)
                .mapToDouble(pack -> pack.dimensions().volumeInCubicMeters())
                .sum();

        BigDecimal totalAmount = BigDecimal.valueOf(totalVolume)
                .multiply(PRICE_PER_CUBIC_METER)
                .setScale(2, RoundingMode.HALF_UP);

        return new Price(totalAmount, shipment.currency());
    }
}
