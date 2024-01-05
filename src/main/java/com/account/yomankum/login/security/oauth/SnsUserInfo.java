package com.account.yomankum.login.security.oauth;

public interface SnsUserInfo {

    String getUUIDKey();
    Sns getSnsName();
    String getEmail();

}
