package com.account.yomankum.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignUpDto {

    @NotBlank
    @Size(min = 4, max = 15)
    @Pattern(regexp = "^[a-z0-9]+$")
    private String username;
    @NotBlank
    @Size(min = 6, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String password;
}
