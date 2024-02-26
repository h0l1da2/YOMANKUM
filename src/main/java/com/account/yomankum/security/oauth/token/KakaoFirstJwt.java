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
public class KakaoFirstJwt implements JwtValue {

    @Value("${token.keys.kakao.kid.first}")
    private String kid;
    @Value("${token.keys.kakao.kty.first}")
    private String kty;
    @Value("${token.keys.kakao.alg.first}")
    private String alg;
    @Value("${token.keys.kakao.use.first}")
    private String use;
    @Value("${token.keys.kakao.n.first}")
    private String n;
    @Value("${token.keys.kakao.e.first}")
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
