package com.account.yomankum.config.jwt;

import com.account.yomankum.domain.Name;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenProvider tokenProvider;
    private final TokenParser tokenParser;

    @Override
    public String creatToken(Long id, String username, Name name) {
        return tokenProvider.createToken(id, username, name);
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

        Name name = Name.ROLE_USER;

        if (role.equals(Name.ROLE_ADMIN)) {
            name = Name.ROLE_ADMIN;
        }

        return tokenProvider.createToken(id, username, name);
    }

    @Override
    public boolean tokenValid(String token) {
        return tokenParser.isValid(token);
    }

    @Override
    public String getUsernameByToken(String token) {
        return tokenParser.getUsername(token);
    }

    @Override
    public Long getIdByToken(String token) {
        return tokenParser.getId(token);
    }


}