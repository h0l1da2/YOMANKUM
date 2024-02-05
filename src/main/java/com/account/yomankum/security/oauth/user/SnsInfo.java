package com.account.yomankum.security.oauth.user;

import com.account.yomankum.security.oauth.type.Sns;
import com.account.yomankum.security.oauth.type.TokenProp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:application.yml")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnsInfo {

    // SNS Info interface
    // GoogleInfo
    // NaverInfo
    // KakaoInfo

    @Getter
    private String clientId;
    @Getter
    private String clientSecret;
    @Getter
    private String redirectUri;
    @Getter
    private String authUri;
    @Getter
    private String tokenUri;
    @Getter
    private List<String> scope = new ArrayList<>();


    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;
    @Value("${spring.security.oauth2.client.provider.google.authorization-uri}")
    private String googleAuthUri;
    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String googleTokenUri;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;
    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String naverRedirectUri;
    @Value("${spring.security.oauth2.client.provider.naver.authorization-uri}")
    private String naverAuthUri;
    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String naverTokenUri;
    @Getter
    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String naverProfileApiUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;
    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String kakaoAuthUri;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoTokenUri;

    public String responseType() {
        return TokenProp.CODE.getName();
    }

    public SnsInfo(String sns) {
        if (sns.equals(Sns.GOOGLE.name())) {
            this.clientId = googleClientId;
            this.clientSecret = googleClientSecret;
            this.redirectUri = googleRedirectUri;
            this.authUri = googleAuthUri;
            this.tokenUri = googleTokenUri;
            scope.add("email");
            scope.add("profile");
        }
        if (sns.equals(Sns.KAKAO.name())) {
            this.clientId = kakaoClientId;
            this.clientSecret = kakaoClientSecret;
            this.redirectUri = kakaoRedirectUri;
            this.authUri = kakaoAuthUri;
            this.tokenUri = kakaoTokenUri;
            scope.add("profile_nickname");
            scope.add("account_email");
            scope.add("openid");
        }
        if (sns.equals(Sns.NAVER.name())) {
            this.clientId = naverClientId;
            this.clientSecret = naverClientSecret;
            this.redirectUri = naverRedirectUri;
            this.authUri = naverAuthUri;
            this.tokenUri = naverTokenUri;
            scope.add("email");

        }
    }

}