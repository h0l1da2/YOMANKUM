package com.account.yomankum.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record EmailCodeDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String code
) {}
