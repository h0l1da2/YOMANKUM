package com.account.yomankum.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailCodeDto(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String code
) {}
