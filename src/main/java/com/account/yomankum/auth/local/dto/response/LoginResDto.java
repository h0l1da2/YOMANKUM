package com.account.yomankum.auth.local.dto.response;

import com.account.yomankum.user.domain.User;
import lombok.Builder;

@Builder
public record LoginResDto(
        String accessToken,
        String refreshToken,
        Long id,
        String nickname
) {
    public static LoginResDto of(String accessToken, String refreshToken, User user) {
        return LoginResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(user.getNickname())
                .id(user.getId())
                .build();
    }
}
