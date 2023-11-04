package com.account.yomankum.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Payment {

    CARD("카드"), CASH("현금"), ECT("기타");

    private String title;

}
