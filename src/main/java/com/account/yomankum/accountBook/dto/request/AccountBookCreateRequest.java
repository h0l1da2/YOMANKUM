package com.account.yomankum.accountBook.dto.request;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.AccountBookType;
import jakarta.validation.constraints.NotBlank;

public record AccountBookCreateRequest (
        @NotBlank
        String name,
        @NotBlank
        AccountBookType type
){
    public AccountBook toEntity(){
        return AccountBook.builder()
                .name(name)
                .type(type)
                .build();
    }
}
