package com.account.yomankum.auth.oauth.infra.kakao;

import com.account.yomankum.auth.oauth.domain.memberClient.OauthUserClient;
import com.account.yomankum.auth.oauth.infra.kakao.config.KakaoAuthConfig;
import com.account.yomankum.auth.oauth.infra.kakao.response.KakaoUserResponse;
import com.account.yomankum.auth.oauth.infra.kakao.response.KakaoToken;
import com.account.yomankum.user.domain.AuthType;
import com.account.yomankum.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class KakaoUserClient implements OauthUserClient {

    private final KakaoApiClient kakaoApiClient;
    private final KakaoAuthConfig authConfig;

    @Override
    public AuthType support() {
        return AuthType.KAKAO;
    }

    @Override
    public User findUser(String code) {
        KakaoToken token = kakaoApiClient.fetchToken(makeRequestTokenParam(code));
        KakaoUserResponse userResponse = kakaoApiClient.fetchUser("Bearer " + token.getAccessToken());
        return userResponse.toEntity();
    }

    private MultiValueMap<String, String> makeRequestTokenParam(String code){
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("code", code);
        param.add("client_id", authConfig.getClientId());
        param.add("client_secret", authConfig.getClientSecret());
        param.add("redirect_uri", authConfig.getRedirectUri());
        param.add("grant_type", authConfig.getAuthorizationGrantType());
        return param;
    }
}
