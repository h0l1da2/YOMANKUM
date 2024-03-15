package com.account.yomankum.security.dto;

import lombok.Builder;

@Builder
public record TokenResDto(
        String accessToken,
        String refreshToken
) {
}
