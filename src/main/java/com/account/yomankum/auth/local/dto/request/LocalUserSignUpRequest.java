package com.account.yomankum.auth.local.dto.request;

import com.account.yomankum.common.annotation.Password;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.domain.UserType;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Builder
public record LocalUserSignUpRequest(
        @Email
        String email,
        @Password
        String password

){
    public User toEntity() {
        return User.builder()
                .userType(UserType.USER)
                .email(email)
                .password(password)
                .pwdChangeDatetime(LocalDateTime.now())
                .build();
    }
}

