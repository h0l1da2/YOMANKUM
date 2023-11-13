package com.account.yomankum.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@PropertySource("classpath:application.yml")
public class TokenParser {
    private final JwtParser jwtParser;
    public TokenParser(@Value("${token.secret.key}") String secretKey) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parser().setSigningKey(key).build();
    }

    public boolean isValid(String token) {
        Claims claims = getClaims(token);
        if (claims.getSubject() != null) {
            return !claims.getExpiration().before(new Date());
        }
        return false;
    }

    public Long getId(String token) {
        Claims claims = getClaims(token);
        return Long.parseLong(claims.get("id", String.class));
    }

    public String getNickname(String token) {
        Claims claims = getClaims(token);
        return claims.get("nickname", String.class);
    }

    public String getRole(String token) {
        Claims claims = getClaims(token);
        return claims.get("role", String.class);
    }

    private Claims getClaims(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims;
    }
}
