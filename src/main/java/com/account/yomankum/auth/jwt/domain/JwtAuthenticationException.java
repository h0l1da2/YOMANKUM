package com.account.yomankum.auth.jwt.domain;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}