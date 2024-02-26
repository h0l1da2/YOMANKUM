package com.account.yomankum.security.oauth.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:application.yml")
@NoArgsConstructor
public class GoogleFirstJwt implements JwtValue {

    @Value("${token.keys.google.kid.first}")
    private String kid;
    @Value("${token.keys.google.kty.first}")
    private String kty;
    @Value("${token.keys.google.alg.first}")
    private String alg;
    @Value("${token.keys.google.use.first}")
    private String use;
    @Value("${token.keys.google.n.first}")
    private String n;
    @Value("${token.keys.google.e.first}")
    private String e;

    @Override
    public String getKid() {
        return this.kid;
    }

    @Override
    public String getKty() {
        return this.kty;
    }

    @Override
    public String getAlg() {
        return this.alg;
    }

    @Override
    public String getUse() {
        return this.use;
    }

    @Override
    public String getN() {
        return this.n;
    }

    @Override
    public String getE() {
        return this.e;
    }

}
