package com.account.yomankum.accountBook.domain.record;

import lombok.Getter;

@Getter
public enum RecordType {

    INCOME("수입"),
    EXPENDITURE("지출");

    private final String title;

    RecordType(String title){
        this.title = title;
    }

}
