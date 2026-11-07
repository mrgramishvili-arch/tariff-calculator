package ru.skillbox.usecase;

import org.junit.jupiter.api.Test;
import ru.skillbox.domain.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculateUseCaseTest {

    private final CalculateUseCase useCase = new CalculateUseCase();

    @Test
    void shouldCalculateByWeightWhenNoDimensions() {
        Pack pack = new Pack(Weight.fromGrams(2000), null);
        Shipment shipment = new Shipment(List.of(pack), new Currency("RUB"), null, null);

        Price result = useCase.calc(shipment);
        // 2000 * 0.36 = 720
        assertEquals(new BigDecimal("720.00"), result.amount());
    }

    @Test
    void shouldApplyMinimalPrice() {
        Pack pack = new Pack(Weight.fromGrams(100), null);
        Shipment shipment = new Shipment(List.of(pack), new Currency("RUB"), null, null);

        Price result = useCase.calc(shipment);
        // 100 * 0.36 = 36, но минимальная 500
        assertEquals(new BigDecimal("500.00"), result.amount());
    }

    @Test
    void shouldUseMaxBetweenWeightAndVolume() {
        // Вес 1000г = 360р. Объем: 1000х1000х1000 = 1м3 = 1000р.
        // Выберет объем (1000)
        Pack pack = new Pack(
                Weight.fromGrams(1000),
                new OuterDimensions(Length.fromMillimeter(1000), Length.fromMillimeter(1000), Length.fromMillimeter(1000))
        );
        Shipment shipment = new Shipment(List.of(pack), new Currency("RUB"), null, null);

        Price result = useCase.calc(shipment);
        assertEquals(new BigDecimal("1000.00"), result.amount());
    }
}
