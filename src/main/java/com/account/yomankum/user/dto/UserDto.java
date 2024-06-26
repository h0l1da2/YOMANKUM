package com.account.yomankum.user.dto;

import com.account.yomankum.common.annotation.Password;
import com.account.yomankum.user.domain.Role;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.domain.UserType;
import com.account.yomankum.user.domain.type.RoleName;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.time.Instant;

// TODO 합치기
public record UserDto() {
    @Builder
    public record UserSignUpDto (
            @Email
            String email,
            @Password
            String password

    ){
        public User toEntity(String encodedPassword) {
            return User.builder()
                    .role(new Role(RoleName.ROLE_USER))
                    .userType(UserType.USER)
                    .email(email)
                    .password(encodedPassword)
                    .pwdChangeDatetime(Instant.now())
                    .build();
        }
    }

    @Builder
    public record UserLoginDto(
            @Email
            String email,
            @Password
            String password
    ) {}
}
