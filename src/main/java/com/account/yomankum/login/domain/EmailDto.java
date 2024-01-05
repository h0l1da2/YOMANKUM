package com.account.yomankum.login.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record EmailDto(
        @NotBlank
        @Email
        String email
) {}
