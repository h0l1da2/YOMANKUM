package com.account.yomankum.auth.oauth.infra.oauth.kakao.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oauth.provider.kakao")
public class KakaoProviderConfig {

    private String authorizationUri;
    private String tokenUri;
    private String userInfoUrl;
    private String userNameAttribute;

}
