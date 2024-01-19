package com.account.yomankum.security.jwt;

import com.account.yomankum.exception.SnsException;
import com.account.yomankum.security.domain.GoogleJwt;
import com.account.yomankum.security.domain.JwtValue;
import com.account.yomankum.security.domain.KakaoJwt;
import com.account.yomankum.security.domain.Sns;
import com.account.yomankum.domain.enums.Name;
import com.account.yomankum.web.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenProvider tokenProvider;
    private final TokenParser tokenParser;

    @Override
    public String creatToken(Long id, String nickname, Name name) {
        return tokenProvider.createToken(id, nickname, name);
    }

    @Override
    public String createRefreshToken() {
        return tokenProvider.createRefreshToken();
    }

    @Override
    public String reCreateToken(String token) {
        Long id = tokenParser.getId(token);
        String nickname = tokenParser.getNickname(token);
        String role = tokenParser.getRole(token);

        Name name = Name.ROLE_USER;

        if (role.equals(Name.ROLE_ADMIN)) {
            name = Name.ROLE_ADMIN;
        }

        return tokenProvider.createToken(id, nickname, name);
    }

    @Override
    public boolean tokenValid(String token) {
        return tokenParser.isValid(token);
    }

    @Override
    public String getNicknameByToken(String token) {
        return tokenParser.getNickname(token);
    }

    @Override
    public Long getIdByToken(String token) {
        return tokenParser.getId(token);
    }

    @Override
    public String getSnsUUID(String sns, String token) throws SnsException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {

        String kid = tokenParser.getSnsTokenSecret(token, "header", "kid");

        JwtValue jwtValue = null;

        // kid First인지 Second인지 확인하고, 해당 객체 쓰기
        if (sns.equals(Sns.KAKAO.name()))
            jwtValue = new KakaoJwt();

        if (sns.equals(Sns.GOOGLE.name()))
            jwtValue = new GoogleJwt();
        else
            throw new SnsException(ResponseCode.SNS_DOESNT_EXIST);

        if (kid.equals(jwtValue.getFirstKid()))
            jwtValue.jwkSetting("first");

        else if (kid.equals(jwtValue.getSecondKid()))
            jwtValue.jwkSetting("second");

        else throw new SnsException(ResponseCode.SNS_KEY_NOT_VALID);



        return tokenParser.getSnsUUID(jwtValue, token);
    }

}