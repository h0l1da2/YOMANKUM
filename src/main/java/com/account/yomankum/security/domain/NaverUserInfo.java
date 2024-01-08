package com.account.yomankum.security.domain;

import com.account.yomankum.security.domain.Sns;
import com.account.yomankum.security.domain.SnsUserInfo;

import java.util.Map;

public class NaverUserInfo implements SnsUserInfo {

    private final Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getUUIDKey() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public Sns getSnsName() {
        return Sns.NAVER;
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

}

