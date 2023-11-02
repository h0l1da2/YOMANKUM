package com.account.yomankum.config.jwt;

import com.account.yomankum.domain.Name;

public interface TokenService {

    String creatToken(Long id, String username, Name name);
    String createRefreshToken();
    String reCreateToken(String token);
    boolean tokenValid(String token);
    String usernameByToken(String token);
    Long getIdByToken(String token);
}
