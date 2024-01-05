package com.account.yomankum.security.oauth;

public interface SnsUserInfo {

    String getUUIDKey();
    Sns getSnsName();
    String getEmail();

}
