package com.account.yomankum.security.service;

import com.account.yomankum.user.domain.type.RoleName;
import com.account.yomankum.common.exception.status4xx.SnsException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

public interface TokenService {

    String creatToken(Long id, String nickname, RoleName name);
    String createRefreshToken();
    String reCreateToken(String token);
    boolean tokenValid(String token);
    String getNicknameByToken(String token);
    Long getIdByToken(String token);
    String getSnsUUID(String sns, String token) throws SnsException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException;
}
