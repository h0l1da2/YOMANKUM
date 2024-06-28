package com.account.yomankum.accountBook.dto.request;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.tag.DefaultTag;
import com.account.yomankum.accountBook.domain.tag.Tag;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AccountBookCreateRequest (
        @NotBlank
        String name
){
    public AccountBook toAccountBookEntity(){
        AccountBook accountBook = AccountBook.builder()
                .name(name)
                .build();
        List<Tag> defaultTags = DefaultTag.getDefaultMainTags();
        accountBook.addTags(defaultTags);
        return accountBook;
    }

}
