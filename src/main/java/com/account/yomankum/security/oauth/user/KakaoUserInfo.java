package com.account.yomankum.security.oauth.user;

import com.account.yomankum.security.oauth.type.Sns;
import com.account.yomankum.security.oauth.type.TokenProp;

import java.util.Map;

public class KakaoUserInfo implements SnsUserInfo {

    private final Map<String, Object> attributes;
    private final Map<String, Object> attributesAccount;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.attributesAccount = (Map<String, Object>) attributes.get(TokenProp.KAKAO_ACCOUNT.getName());
    }
    @Override
    public String getUUIDKey() {
        return String.valueOf(attributes.get(TokenProp.ID.getName()));
    }

    @Override
    public Sns getSnsName() {
        return Sns.KAKAO;
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributesAccount.get(TokenProp.EMAIL.getName()));
    }

}
