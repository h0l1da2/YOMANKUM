package com.account.yomankum.exception;

public class IncorrectLoginException extends Exception {
    public IncorrectLoginException() {
    }

    public IncorrectLoginException(String message) {
        super(message);
    }

    public IncorrectLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectLoginException(Throwable cause) {
        super(cause);
    }
}
