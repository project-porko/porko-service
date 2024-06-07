package io.porko;

import io.porko.auth.config.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class PorkoServiceApp {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application, application-service");
        SpringApplication.run(PorkoServiceApp.class, args);
    }
}
