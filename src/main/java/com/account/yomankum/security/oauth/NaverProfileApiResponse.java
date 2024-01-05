package com.account.yomankum.security.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NaverProfileApiResponse {

    @JsonProperty("resultcode")
    private String resultCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("response")
    private NaverProfileResponse response;

    @Getter
    public static class NaverProfileResponse {
        @JsonProperty("id")
        private String id;

        @JsonProperty("email")
        private String email;

        @JsonProperty("mobile")
        private String mobile;

        @JsonProperty("mobile_e164")
        private String mobileE164;

        @JsonProperty("name")
        private String name;

        @JsonProperty("nickname")
        private String nickname;

        @JsonProperty("gender")
        private String gender;

        @JsonProperty("age")
        private String age;
        @JsonProperty("birthday")
        private String birthday;
        @JsonProperty("profile_image")
        private String profileImage;
        @JsonProperty("birthyear")
        private String birthYear;

    }


}
