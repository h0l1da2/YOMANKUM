package com.account.yomankum.auth.oauth.dto;

import com.account.yomankum.user.domain.AuthType;

public record OauthLoginRequest(
        AuthType type,
        String code
) {
}
