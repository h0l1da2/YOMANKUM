package com.account.yomankum.auth.common.jwt;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}