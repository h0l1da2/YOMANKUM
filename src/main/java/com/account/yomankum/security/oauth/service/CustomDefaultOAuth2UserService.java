package com.account.yomankum.security.oauth.service;

import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.exception.InternalErrorException;
import com.account.yomankum.security.oauth.type.Sns;
import com.account.yomankum.security.oauth.user.GoogleUserInfo;
import com.account.yomankum.security.oauth.user.KakaoUserInfo;
import com.account.yomankum.security.oauth.user.NaverUserInfo;
import com.account.yomankum.security.oauth.user.SnsUserInfo;
import com.account.yomankum.security.service.CustomUserDetails;
import com.account.yomankum.security.service.SnsUserService;
import com.account.yomankum.user.domain.SnsUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomDefaultOAuth2UserService extends DefaultOAuth2UserService {

    private final SnsUserService snsUserService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("유저 서비스 시작");

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String client = userRequest.getClientRegistration().getRegistrationId();

        // userInfo 에 해당 sns 에 맞는 info 객체를 넣는 메소드
        SnsUserInfo userInfo = clientUserInfoCheck(oAuth2User, client);

        // sns이름 + pk가 memberId
        String uuidKey = userInfo.getUUIDKey();
        String email = userInfo.getEmail();
        Sns sns = userInfo.getSnsName();
        SnsUser user = snsUserService.login(sns, uuidKey);

        // 기존 가입 멤버가 아니라면 ?
        if (user == null) {
            log.info("{} 유저 가입 : {}", sns, uuidKey);
            user = snsUserService.signUp(sns, email, uuidKey);
        }
        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }

    private SnsUserInfo clientUserInfoCheck(OAuth2User oAuth2User, String client) {

        String upperCaseSns = client.toUpperCase();

        if(upperCaseSns.equals(Sns.GOOGLE.name())) {
            return new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(upperCaseSns.equals(Sns.NAVER.name())) {
            return new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        } else if (upperCaseSns.equals(Sns.KAKAO.name())) {
            return new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            log.error("해당 SNS 찾을 수 없음 : {} ", upperCaseSns);
            throw new InternalErrorException(Exception.SERVER_ERROR);
        }
    }

}

