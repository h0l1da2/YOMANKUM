package com.account.yomankum.auth.jwt.service;

import com.account.yomankum.auth.jwt.domain.TokenProperty;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class TokenParser {

    private final JwtParser jwtParser;

    public TokenParser(@Value("${token.secret.key}") String secretKey) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parser().verifyWith(key).clock(Date::new).build();
    }

    public boolean isValid(String token) {
        Map<String, Object> payload = getPayload(token);
        if (!payload.isEmpty()) {
            Long exp = (Long) payload.get("exp");
            Instant instant = Instant.ofEpochSecond(exp);
            LocalDateTime expLocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            return !expLocalDateTime.isBefore(LocalDateTime.now());
        }
        return false;
    }

    public Long getUserId(String token) {
        Map<String, Object> payload = getPayload(token);
        return Long.parseLong((String) payload.get(TokenProperty.USER_ID.getKey()));
    }

    public String getNickname(String token) {
        Map<String, Object> payload = getPayload(token);
        return (String) payload.get(TokenProperty.NICKNAME.getKey());
    }

    public String getRole(String token) {
        Map<String, Object> payload = getPayload(token);
        return (String) payload.get(TokenProperty.ROLE.getKey());
    }

    private Map<String, Object> getPayload(String token) {
        return (Map<String, Object>) jwtParser.parse(token).getPayload();
    }
}
