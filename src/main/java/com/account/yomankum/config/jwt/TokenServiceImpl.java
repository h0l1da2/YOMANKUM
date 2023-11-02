package com.account.yomankum.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenProvider tokenProvider;
    private final TokenParser tokenParser;

    @Override
    public String creatToken(Long id, String username, String role) {
        return tokenProvider.createToken(id, username, role);
    }

    @Override
    public String createRefreshToken() {
        return tokenProvider.createRefreshToken();
    }

    @Override
    public String reCreateToken(String token) {
        Long id = tokenParser.getId(token);
        String username = tokenParser.getUsername(token);
        String role = tokenParser.getRole(token);
        return tokenProvider.createToken(id, username, role);
    }

    @Override
    public boolean tokenValid(String token) {
        return tokenParser.isValid(token);
    }

    @Override
    public String usernameByToken(String token) {
        return tokenParser.getUsername(token);
    }

    @Override
    public Long getIdByToken(String token) {
        return tokenParser.getId(token);
    }


}