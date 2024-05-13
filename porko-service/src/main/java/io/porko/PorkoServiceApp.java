package io.porko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PorkoServiceApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application, application-service");
        SpringApplication.run(PorkoServiceApp.class, args);
    }
}
