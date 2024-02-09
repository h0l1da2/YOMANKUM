package com.account.yomankum.user.dto.request;

import com.account.yomankum.user.domain.type.Gender;

import java.time.LocalDate;

public record FirstLoginUserInfoSaveDto(
        String nickname,
        Gender gender,
        LocalDate birthDate
) {}
