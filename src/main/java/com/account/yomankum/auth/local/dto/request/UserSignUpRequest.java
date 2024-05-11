package com.account.yomankum.auth.local.dto.request;

import com.account.yomankum.common.annotation.Password;
import com.account.yomankum.user.domain.AuthInfo;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.domain.UserType;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserSignUpRequest(
        @Email
        String email,
        @Password
        String password

){
    public User toEntity() {
        return User.builder()
                .authInfo(AuthInfo.localUser())
                .userType(UserType.USER)
                .email(email)
                .password(password)
                .pwdChangeDatetime(LocalDateTime.now())
                .build();
    }
}

