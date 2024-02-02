package com.account.yomankum.common.exception;

import lombok.Getter;

@Getter
public enum Exception {

    // 가계부
    ACCOUNT_BOOK_NOT_FOUND("가계부를 찾을 수 없습니다.", 404),
    RECORD_BOOK_NOT_FOUND("내역을 찾을 수 없습니다.", 404),

    // 유저
    USER_NOT_FOUND("유저를 찾을 수 없습니다.", 404),

    // 이메일
    EMAIL_NOT_FOUND("이메일을 찾을 수 없습니다.", 404),
    EMAIL_CODE_UN_MATCHED("이메일 코드가 다릅니다.", 404),

    // 서버 에러
    SERVER_ERROR("서버 에러", 500);

    private final String message;
    private final int code;

    Exception(String message, int code){
        this.message = message;
        this.code = code;
    }
}
