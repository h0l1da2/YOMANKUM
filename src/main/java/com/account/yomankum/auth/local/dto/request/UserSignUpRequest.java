package com.account.yomankum.auth.local.dto.request;

import com.account.yomankum.common.annotation.Password;
import com.account.yomankum.user.domain.Role;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.domain.UserType;
import com.account.yomankum.user.domain.type.RoleName;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Builder
public record UserSignUpRequest(
        @Email
        String email,
        @Password
        String password

){
    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .role(new Role(RoleName.ROLE_USER))
                .userType(UserType.USER)
                .email(email)
                .password(passwordEncoder.encode(password))
                .pwdChangeDatetime(LocalDateTime.now())
                .build();
    }
}

