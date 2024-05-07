package com.account.yomankum.auth.oauth.infra.oauth.kakao;


import com.account.yomankum.auth.oauth.infra.oauth.kakao.response.KakaoUserResponse;
import com.account.yomankum.auth.oauth.infra.oauth.kakao.response.KakaoToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

public interface KakaoApiClient {

    @PostExchange(url = "https://kauth.kakao.com/oauth/token", contentType = APPLICATION_FORM_URLENCODED_VALUE)
    KakaoToken fetchToken(@RequestParam MultiValueMap<String, String> params);

    @GetExchange("https://kapi.kakao.com/v2/user/me")
    KakaoUserResponse fetchUser(@RequestHeader(name = AUTHORIZATION) String bearerToken);

}
