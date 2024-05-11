package com.account.yomankum.auth.oauth.infra.kakao.response;

import com.account.yomankum.user.domain.AuthInfo;
import com.account.yomankum.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserResponse {

    private Long id;
    private KakaoAccount kakaoAccount;

    public User toEntity(){
        AuthInfo authInfo = new AuthInfo();
        return User.builder()
                .authInfo(authInfo)
                .nickname(kakaoAccount.profile.nickname)
                .picture(kakaoAccount.profile.profileImageUrl)
                .build();
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public class KakaoAccount{
        Profile profile;

        @Data
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public class Profile{
            String nickname;
            String profileImageUrl;
        }
    }
}