package com.account.yomankum.security.oauth.user;

import com.account.yomankum.security.oauth.type.Sns;

public interface SnsUserInfo {

    String getUUIDKey();
    Sns getSnsName();
    String getEmail();

}
