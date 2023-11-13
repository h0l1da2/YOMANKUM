package com.account.yomankum.config.jwt;

import com.account.yomankum.domain.Name;

public interface TokenService {

    String creatToken(Long id, String nickname, Name name);
    String createRefreshToken();
    String reCreateToken(String token);
    boolean tokenValid(String token);
    String getNicknameByToken(String token);
    Long getIdByToken(String token);
}
