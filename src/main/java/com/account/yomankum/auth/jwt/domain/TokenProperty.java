package com.account.yomankum.auth.jwt.domain;

import lombok.Getter;

@Getter
public enum TokenProperty {


        TYPE("type"),
        BEARER("Bearer "),
        USER_ID("id"),
        NICKNAME("nickname"),
        ROLE("role"),
        EMAIL("email"),
        ;

        private final String key;

        TokenProperty(String key) {
            this.key = key;
        }

}
