package com.account.yomankum.common.exception;

import lombok.Getter;

@Getter
public enum Exception {

    // 가계부
    ACCOUNT_BOOK_NOT_FOUND("가계부를 찾을 수 없습니다.", 404),
    RECORD_BOOK_NOT_FOUND("내역을 찾을 수 없습니다.", 404),

    // 유저
    USER_NOT_FOUND("유저를 찾을 수 없습니다.", 404),
    DUPLICATED_USER("중복 유저입니다.", 400),

    // 이메일
    EMAIL_NOT_FOUND("이메일을 찾을 수 없습니다.", 404),
    EMAIL_CODE_UN_MATCHED("이메일 코드가 다릅니다.", 404),

    // 서버 에러
    SERVER_ERROR("서버 에러", 500),

    // 기타
    REQUEST_NOT_FOUND("요청값을 찾을 수 없습니다.", 404),
    ACCESS_DENIED("권한 없음.", 403),
    TOKEN_NOT_VALID("토큰이 유효하지 않습니다.", 401)

    ;

    private final String message;
    private final int code;

    Exception(String message, int code){
        this.message = message;
        this.code = code;
    }
}
