package com.account.yomankum.security.oauth.token;

public interface JwtValue {

    String getKid();
    String getKty();
    String getAlg();
    String getUse();
    String getN();
    String getE();
}
