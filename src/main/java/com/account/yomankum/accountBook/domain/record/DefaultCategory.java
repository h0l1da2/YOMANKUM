package com.account.yomankum.accountBook.domain.record;

import lombok.Getter;

// 기본 대분류
@Getter
public enum DefaultCategory {

    FOOD("식비"),
    WEAR("의류"),
    BONUS("보너스"),
    MONTHLY_PAY("월급");

    private final String title;

    DefaultCategory(String title){
        this.title = title;
    }

}
