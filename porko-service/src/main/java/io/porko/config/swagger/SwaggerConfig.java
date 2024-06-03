package io.porko.config.swagger;

import static io.jsonwebtoken.Header.JWT_TYPE;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        String tokenType = JWT_TYPE;
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(tokenType);
        Components components = new Components().addSecuritySchemes(tokenType, new SecurityScheme()
            .name(tokenType)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat(tokenType)
        );

        return new OpenAPI()
            .components(new Components())
            .addSecurityItem(securityRequirement)
            .components(components);
    }
}
