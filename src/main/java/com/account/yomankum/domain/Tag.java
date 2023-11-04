package com.account.yomankum.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Tag {

    EAT("식비"), TRANSPORTATION("교통비"), PHONE("통신비"), LIFE("생활비"),
    SAVE("저축"), SALARY("월급"), ETC("기타");

    private String title;

}
