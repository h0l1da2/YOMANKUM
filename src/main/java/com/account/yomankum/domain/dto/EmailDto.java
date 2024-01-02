package com.account.yomankum.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDto(
        @Email
        @NotBlank
        String email
) {}
