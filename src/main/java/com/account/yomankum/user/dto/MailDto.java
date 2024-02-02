package com.account.yomankum.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record MailDto() {

    @Builder
    public record EmailCodeDto(
            @NotBlank
            @Email
            String email,
            @NotBlank
            String code
    ) {}
}
