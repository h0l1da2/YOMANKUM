package com.account.yomankum.exception;

import com.account.yomankum.web.response.ResponseCode;
import lombok.Data;

@Data
public class UserNotFoundException extends Exception {

    private ResponseCode responseCode;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
