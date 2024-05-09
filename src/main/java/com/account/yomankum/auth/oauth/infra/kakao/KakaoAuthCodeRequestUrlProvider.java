package com.account.yomankum.auth.oauth.infra.kakao;

import com.account.yomankum.auth.oauth.domain.oauthCodeRequest.AuthCodeRequestUrlProvider;
import com.account.yomankum.auth.oauth.infra.kakao.config.KakaoAuthConfig;
import com.account.yomankum.auth.oauth.infra.kakao.config.KakaoProviderConfig;
import com.account.yomankum.user.domain.AuthType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoAuthCodeRequestUrlProvider implements AuthCodeRequestUrlProvider {

    private final KakaoProviderConfig providerConfig;
    private final KakaoAuthConfig authConfig;

    @Override
    public AuthType support() {
        return AuthType.KAKAO;
    }

    @Override
    public String getUrl() {
        return UriComponentsBuilder
                .fromUriString(providerConfig.getAuthorizationUri())
                .queryParam("response_type", "code")
                .queryParam("client_id", authConfig.getClientId())
                .queryParam("redirect_uri", authConfig.getRedirectUri())
                .toUriString();
    }
}
