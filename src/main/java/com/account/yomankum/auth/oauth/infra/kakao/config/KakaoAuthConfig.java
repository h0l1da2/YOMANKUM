package com.account.yomankum.auth.oauth.infra.kakao.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oauth.kakao")
public class KakaoAuthConfig {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String authorizationGrantType;
    private String clientAuthenticationMethod;

    private String authorizationUri; // code요청 url
}