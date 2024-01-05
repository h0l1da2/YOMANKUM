package com.account.yomankum.login.service;

import com.account.yomankum.security.oauth.Sns;
import com.account.yomankum.domain.SnsUser;

public interface SnsUserService {

    SnsUser loginCheck(Sns sns, String uuidKey, String email);
    SnsUser signUp(Sns sns, String email, String uuidKey);

}
