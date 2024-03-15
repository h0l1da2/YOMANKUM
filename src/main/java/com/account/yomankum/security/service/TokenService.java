package com.account.yomankum.security.service;

import com.account.yomankum.security.dto.RefreshTokenReqDto;
import com.account.yomankum.security.dto.TokenResDto;
import com.account.yomankum.user.domain.type.RoleName;

public interface TokenService {
    String creatToken(Long id, String nickname, RoleName name);
    String createRefreshToken();
    String reCreateToken(String token);
    boolean tokenValid(String token);
    Long getIdByToken(String token);
    String getSnsUUID(String sns, String token);
    TokenResDto refreshTokenValid(RefreshTokenReqDto dto);
}
