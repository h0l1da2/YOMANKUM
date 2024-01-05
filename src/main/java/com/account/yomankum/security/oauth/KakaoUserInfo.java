package com.account.yomankum.security.oauth;

import java.util.Map;

public class KakaoUserInfo implements SnsUserInfo {

    private final Map<String, Object> attributes;
    private final Map<String, Object> attributesAccount;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
    }
    @Override
    public String getUUIDKey() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public Sns getSnsName() {
        return Sns.KAKAO;
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributesAccount.get("email"));
    }

}
