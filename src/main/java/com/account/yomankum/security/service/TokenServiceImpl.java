package com.account.yomankum.security.service;

import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.exception.InternalErrorException;
import com.account.yomankum.security.oauth.token.GoogleJwt;
import com.account.yomankum.security.oauth.token.JwtValue;
import com.account.yomankum.security.oauth.token.KakaoJwt;
import com.account.yomankum.security.oauth.type.Sns;
import com.account.yomankum.security.token.TokenParser;
import com.account.yomankum.security.token.TokenProvider;
import com.account.yomankum.user.domain.type.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenProvider tokenProvider;
    private final TokenParser tokenParser;

    @Override
    public String creatToken(Long id, String nickname, RoleName name) {
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

        RoleName name = RoleName.ROLE_USER;

        if (role.equals(RoleName.ROLE_ADMIN)) {
            name = RoleName.ROLE_ADMIN;
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
    public String getSnsUUID(String sns, String token) {

        String kid = tokenParser.getSnsTokenSecret(token, "header", "kid");

        JwtValue jwtValue = null;

        // kid First인지 Second인지 확인하고, 해당 객체 쓰기
        if (sns.equals(Sns.KAKAO.name())) {
            jwtValue = new KakaoJwt();
        } else if (sns.equals(Sns.GOOGLE.name())) {
            jwtValue = new GoogleJwt();
        } else {
            log.error("해당 SNS 찾을 수 없음 없음. : {}", sns);
            throw new InternalErrorException(Exception.SERVER_ERROR);
        }

        if (kid.equals(jwtValue.getFirstKid())) {
            jwtValue.jwkSetting("first");
        } else if (kid.equals(jwtValue.getSecondKid())) {
            jwtValue.jwkSetting("second");
        } else {
            log.error("OAuth2 공개 키가 맞지 않음.");
            throw new InternalErrorException(Exception.SERVER_ERROR);
        }

        return tokenParser.getSnsUUID(jwtValue, token);
    }

}