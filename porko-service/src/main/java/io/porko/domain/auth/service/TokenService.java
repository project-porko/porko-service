package io.porko.domain.auth.service;

import io.porko.domain.auth.domain.JwtProperties;
import io.porko.domain.auth.controller.model.PorkoPrincipal;
import io.porko.domain.auth.domain.TokenType;
import io.porko.domain.auth.domain.TokenProvider;
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
