package com.account.yomankum.security.oauth.type;

import lombok.Getter;

@Getter
public enum Tokens {

    TYP("typ"),
    BEARER("Bearer"),

    TOKEN_RESPONSE("tokenResponse"),

    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken"),

    ALGORITHM("SHA256withRSA"),

    ID("id"), NICKNAME("nickname"), ROLE("role"),

    SUB("sub");

    private String realName;

    Tokens(String realName) {
        this.realName = realName;
    }
}
