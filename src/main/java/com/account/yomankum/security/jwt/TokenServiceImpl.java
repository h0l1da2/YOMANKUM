package com.account.yomankum.security.jwt;

import com.account.yomankum.security.oauth.GoogleJwt;
import com.account.yomankum.security.oauth.JwtValue;
import com.account.yomankum.security.oauth.KakaoJwt;
import com.account.yomankum.security.oauth.Sns;
import com.account.yomankum.domain.enums.Name;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenProvider tokenProvider;
    private final TokenParser tokenParser;
    private JwtValue jwtValue;

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
    public String getSnsUUID(String sns, String token) {

        String kid = tokenParser.getSnsTokenSecret(token, "header", "kid");

        // kid First인지 Second인지 확인하고, 해당 객체 쓰기
        if (sns.equals(Sns.KAKAO.name())) {
            jwtValue = new KakaoJwt();
        }
        if (sns.equals(Sns.GOOGLE.name())) {
            jwtValue = new GoogleJwt();
        }

        if (kid.equals(jwtValue.getFirstKid())) {
            jwtValue.jwkSetting("first");
        } else if (kid.equals(jwtValue.getSecondKid())) {
            jwtValue.jwkSetting("second");
        } else {
            log.error("error : {} 공개 키 안 맞음", sns);
        }


        return tokenParser.getSnsUUID(jwtValue, token);
    }

}