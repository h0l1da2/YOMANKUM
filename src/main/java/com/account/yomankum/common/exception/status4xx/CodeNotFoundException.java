package com.account.yomankum.common.exception.status4xx;

import com.account.yomankum.common.exception.BaseException;
import com.account.yomankum.web.response.ResponseCode;

public class CodeNotFoundException extends BaseException {

    public CodeNotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }
}
