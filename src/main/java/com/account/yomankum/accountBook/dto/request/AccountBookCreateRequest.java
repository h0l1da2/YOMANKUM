package com.account.yomankum.accountBook.dto.request;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.AccountBookRole;
import com.account.yomankum.accountBook.domain.AccountBookType;
import com.account.yomankum.accountBook.domain.AccountBookUser;
import com.account.yomankum.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountBookCreateRequest (
        @NotBlank
        String name,
        @NotBlank
        String nickname,
        @NotNull
        AccountBookRole role,
        @NotNull
        AccountBookType type
){
    public AccountBook toAccountBookEntity(){
        return AccountBook.builder()
                .name(name)
                .type(type)
                .build();
    }

    public AccountBookUser toAccountBookUserEntity(AccountBook accountBook, User user){
        return AccountBookUser.builder()
                .accountBook(accountBook)
                .user(user)
                .nickname(nickname)
                .accountBookRole(role)
                .build();
    }

}
