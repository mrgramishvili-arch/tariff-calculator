package ru.skillbox.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ru.skillbox") // Чтобы Spring нашел контроллер в модуле web
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
