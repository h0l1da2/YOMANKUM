package com.account.yomankum.accountBook.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AccountBookInviteRequest(
        @NotBlank
        String email
) {
}
