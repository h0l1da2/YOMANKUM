package com.account.yomankum.user.dto.request;

import com.account.yomankum.user.domain.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserInfoUpdateRequest(
        @NotNull
        String nickname,
        @NotNull
        Gender gender,
        LocalDate birthDate,
        String job,
        Integer salary
) {
}
