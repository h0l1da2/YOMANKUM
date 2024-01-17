package com.account.yomankum.exception;

import com.account.yomankum.web.response.ResponseCode;
import lombok.Data;

@Data
public class SnsException extends Exception {
    private ResponseCode responseCode;
    public SnsException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
