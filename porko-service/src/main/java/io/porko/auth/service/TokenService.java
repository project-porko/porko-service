package io.porko.auth.service;

import io.porko.auth.config.jwt.JwtProperties;
import io.porko.auth.controller.model.PorkoPrincipal;
import io.porko.auth.domain.TokenType;
import io.porko.auth.domain.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtProperties jwtProperties;

    public String issueAccessToken(PorkoPrincipal porkoPrincipal) {
        return TokenProvider.generate(porkoPrincipal, jwtProperties, TokenType.ACCESS_TOKEN);
    }
}
