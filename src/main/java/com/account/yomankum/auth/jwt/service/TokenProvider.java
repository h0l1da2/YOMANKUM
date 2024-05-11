package com.account.yomankum.auth.jwt.service;

import com.account.yomankum.auth.jwt.domain.TokenProperty;
import com.account.yomankum.auth.jwt.domain.TokenType;
import com.account.yomankum.user.domain.UserType;
import io.jsonwebtoken.Claims;
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
public class TokenProvider {

    @Value("${token.valid.time}")
    private int commonTokenValidTime;
    @Value("${token.refresh.valid.time}")
    private int refreshTokenValidTime;

    private Key key;

    public TokenProvider(@Value("${token.secret.key}") String secretKey) {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long id, String username, UserType userType) {
        Claims claims = makeClaims(TokenType.COMMON, id, username, userType);

        return Jwts.builder()
            .header().add(TokenProperty.TYPE.getKey(), TokenProperty.BEARER.getKey()).and()
            .claims(claims)
            .signWith(key)
            .compact();
    }

    public String createRefreshToken(Long userId) {
        Claims claims = makeClaims(TokenType.REFRESH, userId, null, null);

        return Jwts.builder()
                .claims(claims)
                .signWith(key)
                .compact();
    }

    private Claims makeClaims(TokenType tokenType, Long userId, String nickname, UserType userType) {
        Date now = new Date();
        int tokenValidTime = tokenType == TokenType.REFRESH ? refreshTokenValidTime : commonTokenValidTime;

        return Jwts.claims()
                .subject(tokenType.toString())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + tokenValidTime))
                .add(TokenProperty.USER_ID.getKey(), userId.toString())
                .add(TokenProperty.NICKNAME.getKey(), nickname)
                .add(TokenProperty.ROLE.getKey(), userType)
                .build();
    }

}