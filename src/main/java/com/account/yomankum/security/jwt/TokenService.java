package com.account.yomankum.security.jwt;

import com.account.yomankum.domain.enums.Name;
import com.account.yomankum.exception.SnsException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

public interface TokenService {

    String creatToken(Long id, String nickname, Name name);
    String createRefreshToken();
    String reCreateToken(String token);
    boolean tokenValid(String token);
    String getNicknameByToken(String token);
    Long getIdByToken(String token);
    String getSnsUUID(String sns, String token) throws SnsException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException;
}
