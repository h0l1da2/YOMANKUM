package com.account.yomankum.config.oauth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.yml")
@Getter
public class KakaoJwk implements JwtValue {

    @Value("${token.keys.kakao.kid.first}")
    private String kidFirst;
    @Value("${token.keys.kakao.kty.first}")
    private String ktyFirst;
    @Value("${token.keys.kakao.alg.first}")
    private String algFirst;
    @Value("${token.keys.kakao.use.first}")
    private String useFirst;
    @Value("${token.keys.kakao.n.first}")
    private String nFirst;
    @Value("${token.keys.kakao.e.first}")
    private String eFirst;

    @Value("${token.keys.kakao.kid.second}")
    private String kidSecond;
    @Value("${token.keys.kakao.kty.second}")
    private String ktySecond;
    @Value("${token.keys.kakao.alg.second}")
    private String algSecond;
    @Value("${token.keys.kakao.use.second}")
    private String useSecond;
    @Value("${token.keys.kakao.n.second}")
    private String nSecond;
    @Value("${token.keys.kakao.e.second}")
    private String eSecond;

    private String kid;
    private String kty;
    private String alg;
    private String use;
    private String n;
    private String e;

    public KakaoJwk() {

    }

    public void jwkSetting(String seq) {

        if (seq.equals("first")) {
            this.kid = kidFirst;
            this.kty = ktyFirst;
            this.alg = algFirst;
            this.use = useFirst;
            this.n = nFirst;
            this.e = eFirst;
        }
        if (seq.equals("second")) {
            this.kid = kidSecond;
            this.kty = ktySecond;
            this.alg = algSecond;
            this.use = useSecond;
            this.n = nSecond;
            this.e = eSecond;
        }
    }
}
