package com.account.yomankum.auth.jwt.service;

import com.account.yomankum.user.domain.UserType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenParser tokenParser;
    private final TokenProvider tokenProvider;

    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("Authorization");
    }

    public boolean tokenValid(String token) {
        return tokenParser.isValid(token);
    }

    public Authentication getAuthentication(String token) {
        Long userId = getUserId(token);
        UserDetails userDetails =  new org.springframework.security.core.userdetails.User(String.valueOf(userId), "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Long getUserId(String token) {
        return tokenParser.getUserId(token);
    }

    public String creatToken(Long id, String nickname, UserType userType) {
        return tokenProvider.createToken(id, nickname, userType);
    }

    public String createRefreshToken(Long userId) {
        return tokenProvider.createRefreshToken(userId);
    }
}
