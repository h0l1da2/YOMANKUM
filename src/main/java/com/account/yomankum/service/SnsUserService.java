package com.account.yomankum.service;

import com.account.yomankum.config.oauth.Sns;
import com.account.yomankum.domain.SnsUser;

public interface SnsUserService {

    SnsUser loginCheck(Sns sns, String uuidKey, String email);
    SnsUser signUp(Sns sns, String email, String uuidKey);

}
