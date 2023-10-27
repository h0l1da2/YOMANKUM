package com.account.yomankum.exception;

public class UserDuplicateException extends Exception {

    public UserDuplicateException() {
    }

    public UserDuplicateException(String message) {
        super(message);
    }

    public UserDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
