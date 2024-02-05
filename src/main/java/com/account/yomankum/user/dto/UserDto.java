package com.account.yomankum.user.dto;

import com.account.yomankum.user.domain.Role;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.domain.type.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

// TODO 합치기
public record UserDto() {

    @Builder
    public record UserSignUpDto (
            @NotBlank
            @Email
            String email,
            @NotBlank
            @Size(min = 6, max = 20)
            @Pattern(regexp = "^[a-zA-Z0-9]+$")
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
    public record LoginDto(
            @NotBlank
            @Email
            String email,
            @NotBlank
            @Size(min = 6, max = 20)
            @Pattern(regexp = "^[a-zA-Z0-9]+$")
            String password
    ) {}
}
