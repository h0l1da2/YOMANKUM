package com.account.yomankum.config.oauth;

import com.account.yomankum.config.CustomUserDetails;
import com.account.yomankum.domain.Name;
import com.account.yomankum.domain.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomDefaultOAuth2UserService extends DefaultOAuth2UserService {

//    private final MemberJoinService memberJoinService;
//
//    public CustomDefaultOAuth2UserService(MemberJoinService memberJoinService) {
//        this.memberJoinService = memberJoinService;
//    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        System.out.println("유저서비스시작");
//
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        String client = userRequest.getClientRegistration().getRegistrationId();
//
//        // userInfo 에 해당 sns 에 맞는 info 객체를 넣는 메소드
//        SnsUserInfo userInfo = clientUserInfoCheck(oAuth2User, client);
//
//        // sns이름 + pk가 memberId
//        String pkey = userInfo.getPkey();
//        String memberId = userInfo.getMemberId();
//        Member member = memberJoinService.findMyAccountMemberId(memberId);
//
//        // 기존 가입 멤버가 아니라면 ?
//        if (member == null) {
//            System.out.println("멤버를 가입시키자");
//            String nickname = userInfo.getSnsName()+"_"+pkey.substring(0, 6);
//            String password = pkey+ UUID.randomUUID();
//            String name = userInfo.getName();
//            String email = userInfo.getEmail();
//            String phone = userInfo.getMobile();
//            Sns sns = userInfo.getSnsName();
//            Date now = new Date();
//            Member newMember =
//                    getNewSnsMember
//                            (memberId, nickname, password, name, email, phone, sns, now);
//
//            member = memberJoinService.joinMember(newMember);
//        }
//        return new CustomUserDetails(member, oAuth2User.getAttributes());
        return (OAuth2User) new CustomUserDetails(User.builder().build());
    }
//
//    private Member getNewSnsMember(String memberId, String nickname, String password, String name, String email, String phone, Sns sns, Date now) {
//        return Member.builder()
//                .memberId(memberId)
//                .password(password)
//                .name(name)
//                .nickname(nickname)
//                .email(email)
//                .phone(phone)
//                .address(new Address())
//                .role(new Role(Name.ROLE_USER))
//                .lastLogin(now)
//                .joinDate(now)
//                .pwdModifyDate(now)
//                .sns(sns)
//                .build();
//    }
//
//    public SnsUserInfo clientUserInfoCheck(OAuth2User oAuth2User, String client) {
//        if(client.equals("google")) {
//            return new GoogleUserInfo(oAuth2User.getAttributes());
//        }
//        if(client.equals("naver")) {
//            return new NaverUserInfo(
//                    (Map<String, Object>) oAuth2User.getAttributes().get("response"));
//        }
//        if(client.equals("kakao")) {
//            return new KakaoUserInfo(oAuth2User.getAttributes());
//        }
//        return null;
//    }

}

