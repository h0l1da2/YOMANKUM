package com.account.yomankum.auth.local.dto.request;

public record PasswordChangeRequest(
        String email,
        String code,
        String password
) {
}
