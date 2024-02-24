package com.account.yomankum.security.service;

import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.exception.InternalErrorException;
import com.account.yomankum.security.oauth.token.*;
import com.account.yomankum.security.oauth.type.Sns;
import com.account.yomankum.security.token.TokenParser;
import com.account.yomankum.security.token.TokenProvider;
import com.account.yomankum.user.domain.type.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${token.keys.google.kid.first}")
    private String googleFirstKid;
    @Value("${token.keys.google.kid.second}")
    private String googleSecondKid;
    @Value("${token.keys.kakao.kid.first}")
    private String kakaoFirstKid;
    @Value("${token.keys.kakao.kid.second}")
    private String kakaoSecondKid;

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

        if (role.equals(RoleName.ROLE_ADMIN.name())) {
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
        JwtValue jwtValue = getJwtValue(sns, kid);
        return tokenParser.getSnsUUID(jwtValue, token);
    }

    private JwtValue getJwtValue(String sns, String kid) {
        if (sns.equals(Sns.KAKAO.name())) {
            return getKakaoJwtValue(kid);
        } else if (sns.equals(Sns.GOOGLE.name())) {
            return getGoogleJwtValue(kid);
        } else {
            log.error("해당 SNS 찾을 수 없음 없음. : {}", sns);
            throw new InternalErrorException(Exception.SERVER_ERROR);
        }
    }

    private JwtValue getGoogleJwtValue(String kid) {
        if (kid.equals(googleFirstKid)) {
            return new GoogleFirstJwt();
        } else if (kid.equals(googleSecondKid)) {
            return new GoogleSecondJwt();
        } else {
            log.error("OAuth2 공개 키가 맞지 않음. : {}", Sns.GOOGLE);
            throw new InternalErrorException(Exception.SERVER_ERROR);
        }
    }

    private JwtValue getKakaoJwtValue(String kid) {
        if (kid.equals(kakaoFirstKid)) {
            return new KakaoFirstJwt();
        } else if (kid.equals(kakaoSecondKid)) {
            return new KakaoSecondJwt();
        } else {
            log.error("OAuth2 공개 키가 맞지 않음. : {}", Sns.KAKAO);
            throw new InternalErrorException(Exception.SERVER_ERROR);
        }
    }

}