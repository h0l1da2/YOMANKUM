package com.account.yomankum.exception;

import com.account.yomankum.web.response.ResponseCode;
import lombok.Data;

@Data
public class CodeNotValidException extends Exception {
    private ResponseCode responseCode;

    public CodeNotValidException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
