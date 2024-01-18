package com.account.yomankum.accountBook.domain;

import lombok.Getter;

@Getter
public enum AccountBookType {

    PRIVATE("개인"),
    SHARED("공유");

    private final String title;

    AccountBookType(String title){
        this.title = title;
    }
}
