package io.porko.auth.domain;

import static io.porko.auth.domain.TokenProvider.addMinute;
import static io.porko.config.security.TestSecurityConfig.testPrincipal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.WeakKeyException;
import io.porko.auth.config.jwt.JwtProperties;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Domain:TokenProvider")
class TokenProviderTest extends TokenTestHelper {
    @Test
    @DisplayName("Access Token 생성")
    void generate() {
        // When
        String actual = TokenProvider.generate(testPrincipal, testJwtProperties, TokenType.ACCESS_TOKEN);

        // Then
        Jws<Claims> claimsJws = resolve(actual, BASE_64_ENCODED_TEST_SECRET_KEY);
        Claims claims = claimsJws.getBody();
        JwsHeader header = claimsJws.getHeader();

        assertAll(
            () -> assertThat(header.getType()).as("JWT 토큰 타입").isEqualTo(Header.JWT_TYPE),
            () -> assertThat(header.getAlgorithm()).as("HS 256 해싱 알고리즘").isEqualTo(SignatureAlgorithm.HS256.name()),
            () -> assertThat(claims.getIssuer()).as("발급 주체").isEqualTo(testJwtProperties.issuer()),
            () -> assertThat(claims.getAudience()).as("발급 대상").isEqualTo(testJwtProperties.audience()),
            () -> assertThat(claims.getSubject()).as("발급 목적").isEqualTo(TokenType.ACCESS_TOKEN.name()),
            () -> assertThat(claims.get("id", Long.class)).isEqualTo(testPrincipal.getId()),
            () -> assertThat(claims.get("username", String.class)).isEqualTo(testPrincipal.getUsername()),
            () -> assertThat(claims.getExpiration()).as("만료 일시 : 발급 일자로부터 10분 후").isEqualTo(addMinute(claims.getIssuedAt(), 10))
        );
    }

    @ParameterizedTest(name = "[{index}]{1}:{0}")
    @MethodSource
    @DisplayName("[예외]Signature 값이 올바르지 않은 경우")
    void throwAuthenticationException_WhenInvalidSigningKey(final String signingKey, final String description) {
        // Given
        JwtProperties jwtProperties = new JwtProperties(ISSUER, signingKey, AUDIENCE, EXPIRE_PERIOD_MINUTES.intValue());

        // When & Then
        assertThatExceptionOfType(WeakKeyException.class)
            .isThrownBy(() -> TokenProvider.generate(testPrincipal, jwtProperties, TokenType.ACCESS_TOKEN))
        ;
    }

    private static Stream<Arguments> throwAuthenticationException_WhenInvalidSigningKey() {
        return Stream.of(
            Arguments.of("invalid encoded key", "Base64 signing key가 아닌 경우"),
            Arguments.of("invalid weak key", "Signing key 길이가 올바르지 않은 경우")
        );
    }
}
