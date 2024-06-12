package io.porko;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO : security 추가
@EnableAdminServer
@SpringBootApplication
public class PorkoSpringBootAdminApp {
    public static void main(String[] args) {
        SpringApplication.run(PorkoSpringBootAdminApp.class, args);
    }
}
