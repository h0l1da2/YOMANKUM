package com.account.yomankum.security.jwt;

import com.account.yomankum.domain.enums.Name;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    // 토큰 유효 시간
    @Value("${token.valid.time}")
    private int tokenValidTime;
    // 리프레쉬 토큰 유효 시간
    @Value("${token.refresh.valid.time}")
    private int refreshTokenValidTime;

    private Key key;

    public TokenProvider(@Value("${token.secret.key}") String secretKey) {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long id, String username, Name name) {

        Claims claims = getClaims("accessToken", id, username, name);

        return Jwts.builder()
                .setHeaderParam("typ", "Bearer")
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken() {

        Claims claims = getClaims("refreshToken");

        return Jwts.builder()
                .claims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private Claims getClaims(String tokenType) {
        Date now = new Date();

        Claims claims = Jwts.claims()
                .subject(tokenType)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + refreshTokenValidTime))
                .build();

        return claims;
    }
    private Claims getClaims(String tokenType, Long id, String nickname, Name name) {
        Date now = new Date();

        Claims claims = Jwts.claims()
                .subject(tokenType)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + tokenValidTime))
                .add("id", id.toString())
                .add("nickname", nickname)
                .add("role", name)
                .build();

        return claims;
    }
}