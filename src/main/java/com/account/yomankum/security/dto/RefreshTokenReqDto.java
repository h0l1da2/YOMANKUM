package com.account.yomankum.security.dto;

public record RefreshTokenReqDto(
        String accessToken,
        String refreshToken
) {
}
