package com.account.yomankum.common.exception;

import lombok.Getter;

@Getter
public enum Exception {

    // 가계부
    ACCOUNT_BOOK_NOT_FOUND("가계부를 찾을 수 없습니다.", 404),
    RECORD_BOOK_NOT_FOUND("내역을 찾을 수 없습니다.", 404);

    private final String message;
    private final int code;

    Exception(String message, int code){
        this.message = message;
        this.code = code;
    }
}
