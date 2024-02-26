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
public class GoogleSecondJwt implements JwtValue {

    @Value("${token.keys.google.kid.second}")
    private String kid;
    @Value("${token.keys.google.kty.second}")
    private String kty;
    @Value("${token.keys.google.alg.second}")
    private String alg;
    @Value("${token.keys.google.use.second}")
    private String use;
    @Value("${token.keys.google.n.second}")
    private String n;
    @Value("${token.keys.google.e.second}")
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
