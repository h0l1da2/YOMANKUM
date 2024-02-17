package com.account.yomankum.user.dto.request;

import com.account.yomankum.user.domain.type.Gender;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserInfoUpdateDto(
        @NotNull
        Gender gender,
        LocalDate birthDate,
        String job,
        @NotNull
        Integer salary
) {
}
