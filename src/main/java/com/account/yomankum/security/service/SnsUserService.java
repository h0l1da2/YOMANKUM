package com.account.yomankum.security.service;

import com.account.yomankum.common.exception.status4xx.UserNotFoundException;
import com.account.yomankum.security.oauth.type.Sns;
import com.account.yomankum.user.domain.SnsUser;

public interface SnsUserService {

    SnsUser login(Sns sns, String uuidKey) throws UserNotFoundException;
    SnsUser signUp(Sns sns, String email, String uuidKey);

}
