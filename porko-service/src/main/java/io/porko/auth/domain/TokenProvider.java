package io.porko.auth.domain;

import static io.porko.auth.utils.SigningUtils.getSigningKey;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.porko.auth.config.jwt.JwtProperties;
import io.porko.auth.controller.model.PorkoPrincipal;
import java.util.Calendar;
import java.util.Date;

public class TokenProvider {
    public static final String ID = "id";
    public static final String USERNAME = "username";

    public static String generate(PorkoPrincipal porkoPrincipal, JwtProperties jwtProperties, TokenType tokenType) {
        long currentTimeMillis = System.currentTimeMillis();
        Date issuedAt = new Date(currentTimeMillis);
        Date expiration = addMinute(issuedAt, jwtProperties.expirePeriod());

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer(jwtProperties.issuer())
            .setSubject(tokenType.name())
            .setAudience(jwtProperties.audience())
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .claim(ID, porkoPrincipal.getId())
            .claim(USERNAME, porkoPrincipal.getUsername())
            .signWith(getSigningKey(jwtProperties.secretKey()), SignatureAlgorithm.HS256)
            .compact();
    }

    public static Date addMinute(Date target, int terms) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(target);
        calendar.add(Calendar.MINUTE, terms);
        return calendar.getTime();
    }
}
