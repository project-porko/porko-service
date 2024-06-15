package io.porko.domain.auth.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    String issuer,
    String secretKey,
    String audience,
    int expirePeriod
) {

}
