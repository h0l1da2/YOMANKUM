package com.account.yomankum.security.oauth;

import com.account.yomankum.security.CustomUserDetails;
import com.account.yomankum.domain.SnsUser;
import com.account.yomankum.login.service.SnsUserService;
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
        SnsUser user = snsUserService.loginCheck(sns, email, uuidKey);

        // 기존 가입 멤버가 아니라면 ?
        if (user == null) {
            user = snsUserService.signUp(sns, email, uuidKey);
        }
        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }
    public SnsUserInfo clientUserInfoCheck(OAuth2User oAuth2User, String client) {
        if(client.equals("google")) {
            return new GoogleUserInfo(oAuth2User.getAttributes());
        }
        if(client.equals("naver")) {
            return new NaverUserInfo(
                    (Map<String, Object>) oAuth2User.getAttributes().get("response"));
        }
        if(client.equals("kakao")) {
            return new NaverUserInfo(oAuth2User.getAttributes());
        }
        return null;
    }

}

