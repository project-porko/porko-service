package io.porko.domain.auth.utils;

import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class SigningUtils {
    public static Key getSigningKey(String signingKey) {
        String encodedSigningKey = Base64.getEncoder()
            .withoutPadding()
            .encodeToString(signingKey.getBytes());
        byte[] keyBytes = encodedSigningKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
