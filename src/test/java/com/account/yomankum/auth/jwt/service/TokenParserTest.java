package com.account.yomankum.auth.jwt.service;

import com.account.yomankum.auth.jwt.domain.TokenProperty;
import com.account.yomankum.auth.jwt.domain.TokenType;
import com.account.yomankum.user.domain.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class TokenParserTest {

    private TokenParser tokenParser;
    private final String secretKey = "verysecretkeywhichisverylongenough";
    private String validToken;
    private String expiredToken;

    @BeforeEach
    void setUp() {
        tokenParser = new TokenParser(secretKey);
        validToken = generateToken(60000);
        expiredToken = generateToken(-60000);
    }

    @Test
    void testIsValidTrue() {
        assertTrue(tokenParser.isValid(validToken));
    }

    @Test
    void testIsValidFalse() {
        assertFalse(tokenParser.isValid(expiredToken));
    }

    @Test
    void testGetUserId() {
        Long expectedUserId = 1L;
        assertEquals(expectedUserId, tokenParser.getUserId(validToken));
    }

    @Test
    void testGetNickname() {
        String expectedNickname = "testUser";
        assertEquals(expectedNickname, tokenParser.getNickname(validToken));
    }

    @Test
    void testGetRole() {
        String expectedRole = UserType.USER.toString();
        assertEquals(expectedRole, tokenParser.getRole(validToken));
    }

    private String generateToken(int secondsUntilExpiration) {
        Date now = new Date();
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        Date exp = new Date(now.getTime() + secondsUntilExpiration);
        Claims claims = Jwts.claims()
                .subject(TokenType.COMMON.toString())
                .expiration(exp)
                .add(TokenProperty.USER_ID.getKey(), "1")
                .add(TokenProperty.NICKNAME.getKey(), "testUser")
                .add(TokenProperty.ROLE.getKey(), UserType.USER.toString())
                .build();

        return Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .compact();
    }
}