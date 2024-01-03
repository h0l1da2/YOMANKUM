package com.account.yomankum.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    OK("200 OK"),

    // 로그인
    LOGIN000("200 OK"),
    LOGIN001("JSON DECODE ERROR"),
    LOGIN002("Unsupported language"),
    LOGIN003("No language value or Type"),
    LOGIN004("Invalid data by user"),

    // 이메일
    EMAIL000("200 OK"),
    EMAIL001("JSON DECODE ERROR"),
    EMAIL002("Unsupported language"),
    EMAIL003("No language value or Type"),
    EMAIL004("Invalid data by user"),

    // 가입
    SIGNUP000("200 OK"),
    SIGNUP001("JSON DECODE ERROR"),
    SIGNUP002("User Duplicated"),
    SIGNUP003("No language value or Type"),
    SIGNUP004("Invalid data by user");

    private String message;
}
