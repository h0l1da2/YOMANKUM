package com.account.yomankum.security.token;

import com.account.yomankum.security.oauth.type.TokenProp;
import com.account.yomankum.security.oauth.type.Tokens;
import com.account.yomankum.user.domain.UserType;
import com.account.yomankum.user.domain.type.RoleName;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public String createToken(Long id, String username, UserType userType) {
        Claims claims = getClaims(Tokens.ACCESS_TOKEN, id, username, userType);

    return Jwts.builder()
            .header().add(TokenProp.TYP.getName(), TokenProp.BEARER.getName()).and()
            .claims(claims)
            .signWith(key)
            .compact();
    }

    public String createRefreshToken() {
        Claims claims = getClaims(Tokens.REFRESH_TOKEN);

        return Jwts.builder()
                .claims(claims)
                .signWith(key)
                .compact();
    }

    private Claims getClaims(Tokens tokenType) {
        return Jwts.claims()
                .subject(tokenType.toString())
                .issuedAt(nowDate())
                .expiration(new Date(nowDate().getTime() + refreshTokenValidTime))
                .build();
    }
    private Claims getClaims(Tokens tokenType, Long id, String nickname, UserType userType) {
        return Jwts.claims()
                .subject(tokenType.toString())
                .issuedAt(nowDate())
                .expiration(new Date(nowDate().getTime() + tokenValidTime))
                .add(TokenProp.ID.getName(), id.toString())
                .add(TokenProp.NICKNAME.getName(), nickname)
                .add(TokenProp.ROLE.getName(), userType)
                .build();
    }

    private Date nowDate() {
        Instant instant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}