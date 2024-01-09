package com.account.yomankum.accountBook.domain.record;

import lombok.Getter;

// 기본 대분류
@Getter
public enum DefaultCategory {

    FOOD("식비"),
    WEAR("의류"),
    MONTHLY_PAY("월급");

    private String name;

    DefaultCategory(String name){
        this.name = name;
    }

}
