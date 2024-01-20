package com.account.yomankum.exception;

import com.account.yomankum.web.response.ResponseCode;
import lombok.Data;

@Data
public class CodeNotFoundException extends Exception {

    private ResponseCode responseCode;

    public CodeNotFoundException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

}
