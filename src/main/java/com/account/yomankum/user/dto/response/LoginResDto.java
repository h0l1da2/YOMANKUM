package com.account.yomankum.user.dto.response;

import lombok.Builder;

@Builder
public record LoginResDto(
        String accessToken,
        String refreshToken,
        String nickname
) {
    public static LoginResDto of(String accessToken, String refreshToken, String nickname) {
        return LoginResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(nickname)
                .build();
    }
}
