package com.account.yomankum.user.dto;

import com.account.yomankum.common.annotation.Password;
import com.account.yomankum.user.domain.Role;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.domain.UserType;
import com.account.yomankum.user.domain.type.RoleName;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

// TODO 합치기
public record UserDto() {
    @Builder
    public record UserLoginDto(
            @Email
            String email,
            @Password
            String password
    ) {}
}
