package com.account.yomankum.accountBook.dto.request;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.AccountBookType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountBookCreateRequest (
        @NotBlank
        String name,
        @NotNull
        AccountBookType type
){
    public AccountBook toAccountBookEntity(){
        return AccountBook.builder()
                .name(name)
                .type(type)
                .build();
    }

}
