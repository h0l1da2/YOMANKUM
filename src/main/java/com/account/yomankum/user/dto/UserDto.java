package com.account.yomankum.user.dto;

import com.account.yomankum.common.annotation.Password;
import com.account.yomankum.user.domain.Role;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.domain.type.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

// TODO 합치기
public record UserDto() {

    @Builder
    public record UserSignUpDto (
            @NotBlank
            @Email
            String email,
            @Password
            String password

    ){
        public User toEntity(String encodedPassword) {
            return User.builder()
                    .role(new Role(RoleName.ROLE_USER))
                    .email(email)
                    .password(encodedPassword)
                    .pwdChangeDate(LocalDateTime.now())
                    .build();
        }
    }

    @Builder
    public record UserLoginDto(
            @NotBlank
            @Email
            String email,
            @Password
            String password
    ) {}
}
