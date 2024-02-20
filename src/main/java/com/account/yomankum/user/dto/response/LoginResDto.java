package com.account.yomankum.user.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LoginResDto(
        String accessToken,
        String refreshToken,
        LocalDateTime lastLoginDatetime
) {
    public static LoginResDto of(String accessToken, String refreshToken, LocalDateTime lastLoginDatetime) {
        return LoginResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .lastLoginDatetime(lastLoginDatetime)
                .build();
    }
}
