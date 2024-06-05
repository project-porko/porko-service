package io.porko.config.swagger;

import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .addSecurityItem(securityRequirement())
            .addServersItem(getUrl())
            .components(components())
            .info(info())
            ;
    }

    private Info info() {
        return new Info()
            .title("Porko dev API Docs")
            .description("with url api-dev.porko.store")
            .version("cors test-v2");
    }

    private Components components() {
        return new Components().addSecuritySchemes(JWT_TYPE,
            new SecurityScheme()
                .name(JWT_TYPE)
                .type(HTTP)
                .scheme("bearer")
                .bearerFormat(JWT_TYPE));
    }

    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement().addList(JWT_TYPE);
    }

    private static Server getUrl() {
        return new Server()
            .url("http://api-dev.porko.store")
            .description("api dev server url")
            ;
    }
}
