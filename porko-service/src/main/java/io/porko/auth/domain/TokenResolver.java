package io.porko.auth.domain;

import static io.porko.auth.domain.TokenProvider.ID;
import static io.porko.auth.domain.TokenProvider.USERNAME;
import static io.porko.auth.utils.SigningUtils.getSigningKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import io.porko.auth.config.jwt.JwtProperties;
import io.porko.auth.controller.model.PorkoPrincipal;
import io.porko.auth.exception.AuthErrorCode;
import io.porko.auth.exception.AuthException;

public class TokenResolver {
    public static PorkoPrincipal resolve(String token, JwtProperties jwtProperties) {
        Claims claims = extractClaims(token, jwtProperties);
        return PorkoPrincipal.of(
            claims.get(ID, Long.class),
            claims.get(USERNAME, String.class)
        );
    }

    private static Claims extractClaims(String token, JwtProperties jwtProperties) {
        Claims claims = extractClaims(token, jwtProperties.secretKey());
        validateAudience(claims, jwtProperties.audience());
        return claims;
    }

    private static void validateAudience(Claims claims, String audience) {
        if (isNotValidAudience(claims.getAudience(), audience)) {
            throw new AuthException(AuthErrorCode.UNSUPPORTED_AUDIENCE);
        }
    }

    private static boolean isNotValidAudience(String tokenAudience, String propertiesAudience) {
        return !tokenAudience.equals(propertiesAudience);
    }

    private static Claims extractClaims(String token, String secretKey) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (DecodingException | WeakKeyException | SignatureException | NullPointerException signatureException) {
            throw new AuthException(AuthErrorCode.INVALID_SIGNATURE);
        } catch (MalformedJwtException malformedJwtException) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN_FORMAT);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new AuthException(AuthErrorCode.EXPIRED_TOKEN);
        }
    }
}
