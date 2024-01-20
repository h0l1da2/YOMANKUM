package com.account.yomankum.common.exception;

public class BadRequestException extends RuntimeException{

    private final int code;

    public BadRequestException(Exception exception){
        super(exception.getMessage());
        code = exception.getCode();
    }

}
