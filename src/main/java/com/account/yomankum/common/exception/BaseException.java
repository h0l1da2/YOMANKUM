package com.account.yomankum.common.exception;

import com.account.yomankum.web.response.ResponseCode;
import lombok.Data;

@Data
public class BaseException extends java.lang.Exception {
    public ResponseCode responseCode;
    public BaseException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
