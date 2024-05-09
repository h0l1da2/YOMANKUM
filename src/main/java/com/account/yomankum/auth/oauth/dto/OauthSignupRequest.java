package com.account.yomankum.auth.oauth.dto;

import com.account.yomankum.user.domain.AuthInfo;
import com.account.yomankum.user.domain.AuthType;
import com.account.yomankum.user.domain.User;

public record OauthSignupRequest (

        AuthType authType,
        String oauthId,
        String email
){
    public User toUser(){
        AuthInfo authInfo = new AuthInfo(authType, oauthId);
        return User.builder()
                .email(email)
                .authInfo(authInfo)
                .build();
    }
}
