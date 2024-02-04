package com.account.yomankum.security.oauth.type;

import lombok.Getter;

@Getter
public enum Tokens {


    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken"),
    ;

    private final String name;

    Tokens(String name) {
        this.name = name;
    }
}
