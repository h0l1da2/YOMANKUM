package com.account.yomankum.auth.oauth.exception;

import com.account.yomankum.user.domain.AuthType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OauthLoginFailException extends RuntimeException {

    private final AuthType authType;
    private final String oauthId;
    private String message = "oauth login failed.";

}
