package com.account.yomankum.security.service;

import com.account.yomankum.security.domain.Sns;
import com.account.yomankum.domain.SnsUser;

public interface SnsUserService {

    SnsUser login(Sns sns, String uuidKey);
    SnsUser signUp(Sns sns, String email, String uuidKey);

}
