package io.porko.auth.domain;

import static io.porko.auth.utils.SigningUtils.getSigningKey;
import static java.math.BigInteger.TEN;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.porko.auth.config.jwt.JwtProperties;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class TokenTestHelper {
    protected static final String ISSUER = "test.porko.info";
    protected static final String AUDIENCE = "test-porko-client";
    protected static final Long EXPIRE_PERIOD_MINUTES = TimeUnit.MINUTES.toMinutes(TEN.longValue());
    private static final String TEST_SECRET_KEY = "test-porko-service-secret-key";
    protected static final String BASE_64_ENCODED_TEST_SECRET_KEY = encodeSecretKey(TEST_SECRET_KEY);

    public static JwtProperties testJwtProperties = new JwtProperties(ISSUER, BASE_64_ENCODED_TEST_SECRET_KEY, AUDIENCE,
        EXPIRE_PERIOD_MINUTES.intValue());

    public JwtProperties generateTestJwtProperties(String secretKey) {
        return new JwtProperties(ISSUER, encodeSecretKey(secretKey), AUDIENCE, EXPIRE_PERIOD_MINUTES.intValue());
    }

    private static String encodeSecretKey(String secretKey) {
        return Base64.getEncoder()
            .withoutPadding()
            .encodeToString(secretKey.getBytes());
    }

    protected static Jws<Claims> resolve(String token, String secretKey) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey(secretKey))
            .build()
            .parseClaimsJws(token);
    }
}
