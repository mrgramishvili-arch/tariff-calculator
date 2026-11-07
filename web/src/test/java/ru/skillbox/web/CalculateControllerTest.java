package ru.skillbox.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.skillbox.domain.Currency;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CalculateControllerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void shouldReturn400WithErrorMessageWhenValidationFails() {
        // Проверяем, что наш обработчик ошибок действительно отдаёт 400 и текст ошибки
        IllegalArgumentException exception = new IllegalArgumentException("Weight cannot be negative");

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleIllegalArgument(exception);

        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Weight cannot be negative", response.getBody().get("error"));
    }

    @Test
    void shouldCreateCurrencySuccessfully() {
        // Простая проверка, что домен работает
        Currency currency = new Currency("RUB");
        assertEquals("RUB", currency.code());
    }
}
