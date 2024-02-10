package com.account.yomankum.user.dto.request;

import com.account.yomankum.user.domain.type.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FirstLoginUserInfoSaveDto(
        @NotBlank
        String nickname,
        @NotNull
        Gender gender,
        @NotNull
        LocalDate birthDate
) {}
