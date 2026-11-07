package ru.skillbox.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.domain.*;
import ru.skillbox.usecase.CalculateUseCase;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CalculateController {

    private final CalculateUseCase calculateUseCase = new CalculateUseCase();

    @PostMapping("/calculate")
    public CalculatePackagesResponse calculate(@RequestBody CalculatePackagesRequest request) {

        // Преобразуем DTO в домен
        Currency currency = new Currency(request.currencyCode());

        List<Pack> packs = request.packages().stream()
                .map(p -> {
                    Weight weight = Weight.fromGrams(p.weight());
                    OuterDimensions dims = null;
                    if (p.length() != null && p.width() != null && p.height() != null) {
                        dims = new OuterDimensions(
                                Length.fromMillimeter(p.length()),
                                Length.fromMillimeter(p.width()),
                                Length.fromMillimeter(p.height())
                        );
                    }
                    return new Pack(weight, dims);
                })
                .toList();

        GeoCoordinate dep = null;
        GeoCoordinate dest = null;
        if (request.departure() != null && request.destination() != null) {
            dep = new GeoCoordinate(request.departure().latitude(), request.departure().longitude());
            dest = new GeoCoordinate(request.destination().latitude(), request.destination().longitude());
        }

        Shipment shipment = new Shipment(packs, currency, dep, dest);

        // Вызываем сценарий
        Price price = calculateUseCase.calc(shipment);

        // Возвращаем ответ
        return new CalculatePackagesResponse(
                price.amount(),
                new BigDecimal("500.00"), // минимальная цена из настроек
                price.currency().code()
        );
    }
}