package com.account.yomankum.auth.local.dto;

import com.account.yomankum.user.domain.type.MailType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Builder
    public record EmailRequestDto(
            @NotBlank
            @Email
            String email,
            @NotNull
            MailType mailType
    ) {}
}
