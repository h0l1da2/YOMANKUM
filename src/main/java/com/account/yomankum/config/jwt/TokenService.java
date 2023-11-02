package com.account.yomankum.config.jwt;

public interface TokenService {

    String creatToken(Long id, String username, String role);
    String createRefreshToken();
    String reCreateToken(String token);
    boolean tokenValid(String token);
    String usernameByToken(String token);
    Long getIdByToken(String token);
}
