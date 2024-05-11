package com.account.yomankum.auth.local.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
