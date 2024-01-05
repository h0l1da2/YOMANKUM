package com.account.yomankum.security.oauth;

import java.util.Map;

public class GoogleUserInfo implements SnsUserInfo{

    private final Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    @Override
    public String getUUIDKey() {
        return String.valueOf(attributes.get("sub"));
    }

    @Override
    public Sns getSnsName() {
        return Sns.GOOGLE;
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

}

