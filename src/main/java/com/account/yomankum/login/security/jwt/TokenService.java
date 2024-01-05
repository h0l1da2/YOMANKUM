package com.account.yomankum.login.security.jwt;

import com.account.yomankum.domain.Name;

public interface TokenService {

    String creatToken(Long id, String nickname, Name name);
    String createRefreshToken();
    String reCreateToken(String token);
    boolean tokenValid(String token);
    String getNicknameByToken(String token);
    Long getIdByToken(String token);
    String getSnsUUID(String sns, String token);
}
