package com.account.yomankum.auth.oauth.domain.oauthCodeRequest;

import com.account.yomankum.user.domain.AuthType;

public interface AuthCodeRequestUrlProvider {

    AuthType support();
    String getUrl();
}
