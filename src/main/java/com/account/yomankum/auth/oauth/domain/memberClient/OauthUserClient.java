package com.account.yomankum.auth.oauth.domain.memberClient;

import com.account.yomankum.user.domain.AuthType;
import com.account.yomankum.user.domain.User;

public interface OauthUserClient {

    AuthType support();
    User findUser(String code);
}
