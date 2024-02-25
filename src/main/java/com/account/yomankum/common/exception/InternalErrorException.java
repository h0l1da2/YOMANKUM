package com.account.yomankum.common.exception;

public class InternalErrorException extends RuntimeException {

    private final int code;

    public InternalErrorException(Exception exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
    }

}
