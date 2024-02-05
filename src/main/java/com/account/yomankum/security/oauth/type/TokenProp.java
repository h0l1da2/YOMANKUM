package com.account.yomankum.security.oauth.type;

import lombok.Getter;

@Getter
public enum TokenProp {
    SNS("sns"),

    SUB("sub"),
    TYP("typ"),
    BEARER("Bearer"),

    ALGORITHM("SHA256withRSA"),

    CLIENT("client"),
    CLIENT_ID("client_id"),
    CLIENT_SECRET("client_secret"),
    REDIRECT_URI("redirect_uri"),
    GRANT_TYPE("grant_type"),
    AUTHORIZATION_CODE("authorization_code"),

    KAKAO_ACCOUNT("kakao_account"),

    CODE("code"),
    STATE("state"),

    TOKEN_RESPONSE("tokenResponse"),

    ID("id"),
    NICKNAME("nickname"),
    ROLE("role"),
    EMAIL("email"),
    ;

    private final String name;

    TokenProp(String name) {
        this.name = name;
    }
}
