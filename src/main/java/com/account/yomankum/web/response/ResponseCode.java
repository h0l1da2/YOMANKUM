package com.account.yomankum.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    OK("200 OK"),

    USER_NOT_FOUND("유저를 찾을 수 없습니다."),

    EMAIL_CODE_NOT_MATCHED("이메일 코드가 다릅니다."),
    EMAIL_ERROR("메일을 보낼 수 없습니다."),
    EMAIL_NOT_FOUND("이메일을 찾을 수 없음."),


    NOT_VALID("형식을 확인하세요."),
    
    SNS_DOESNT_EXIST("존재하지 않는 SNS 입니다."),
    TOKEN_NOT_VALID("토큰이 정확하지 않음."),
    SNS_KEY_NOT_VALID("SNS 공개키가 맞지 않음.");

    private String message;
}
