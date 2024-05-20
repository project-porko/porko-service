package io.porko.auth.domain;

import static io.porko.config.security.TestSecurityConfig.testPorkoPrincipal;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import io.porko.auth.config.jwt.JwtProperties;
import io.porko.auth.controller.model.PorkoPrincipal;
import io.porko.auth.exception.AuthErrorCode;
import io.porko.auth.exception.AuthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Domain:TokenResolver")
class TokenResolverTest extends TokenTestHelper {
    private final String token = TokenProvider.generate(testPorkoPrincipal, testJwtProperties, TokenType.ACCESS_TOKEN);

    @Test
    @DisplayName("토큰 검증")
    void resolve() {
        // When
        PorkoPrincipal actual = TokenResolver.resolve(token, testJwtProperties);

        // Then
        assertThat(actual.getId()).isEqualTo(testPorkoPrincipal.getId());
        assertThat(actual.getUsername()).isEqualTo(testPorkoPrincipal.getUsername());
    }

    @Test
    @DisplayName("[예외]Signature가 일치 하지 않는 경우")
    void throwAuthException_WhenNotMatchedSigningKey() {
        // given
        final String notMatchedSecretKey = "not-matched-generated-token-secret-key";
        JwtProperties jwtProperties = generateTestJwtProperties(notMatchedSecretKey);
        String generate = TokenProvider.generate(testPorkoPrincipal, jwtProperties, TokenType.ACCESS_TOKEN);

        // When & Then
        assertThatExceptionOfType(AuthException.class)
            .isThrownBy(() -> TokenResolver.resolve(generate, testJwtProperties))
            .extracting(AuthException::getCode).isEqualTo(AuthErrorCode.INVALID_SIGNATURE.name())
        ;
    }

    @Test
    @DisplayName("[예외]토큰이 만료된 경우")
    void throwAuthException_WhenTokenExpired() {
        // Given
        JwtProperties jwtProperties = new JwtProperties(ISSUER, BASE_64_ENCODED_TEST_SECRET_KEY, AUDIENCE, 0);
        String token = TokenProvider.generate(testPorkoPrincipal, jwtProperties, TokenType.ACCESS_TOKEN);

        // When & Then
        assertThatExceptionOfType(AuthException.class)
            .isThrownBy(() -> TokenResolver.resolve(token, testJwtProperties))
            .extracting(AuthException::getCode).isEqualTo(AuthErrorCode.EXPIRED_TOKEN.name())
        ;
    }

    @Test
    @DisplayName("[예외]지원하지 않는 토큰 발급 대상자인 경우")
    void throwAuthException_WhenUnsupportedAudience() {
        // Given
        JwtProperties jwtProperties = new JwtProperties(ISSUER, BASE_64_ENCODED_TEST_SECRET_KEY, "invalid audience",
            EXPIRE_PERIOD_MINUTES.intValue());
        String token = TokenProvider.generate(testPorkoPrincipal, jwtProperties, TokenType.ACCESS_TOKEN);

        // When & Then
        assertThatExceptionOfType(AuthException.class)
            .isThrownBy(() -> TokenResolver.resolve(token, testJwtProperties))
            .extracting(AuthException::getCode).isEqualTo(AuthErrorCode.UNSUPPORTED_AUDIENCE.name())
        ;
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "header.payload.signature"})
    @DisplayName("[예외]JWT 토큰 형식이 올바르지 않은 경우")
    void throwAuthException_WhenInvalidTokenFormat(final String given) {
        // When & Then
        assertThatExceptionOfType(AuthException.class)
            .isThrownBy(() -> TokenResolver.resolve(given, testJwtProperties))
            .extracting(AuthException::getCode).isEqualTo(AuthErrorCode.INVALID_TOKEN_FORMAT.name())
        ;
    }
}
