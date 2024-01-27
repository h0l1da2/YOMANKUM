package com.account.yomankum.common.exception.status4xx;

import com.account.yomankum.common.exception.BaseException;
import com.account.yomankum.web.response.ResponseCode;

public class SnsException extends BaseException {

    public SnsException(ResponseCode responseCode) {
        super(responseCode);
    }
}
