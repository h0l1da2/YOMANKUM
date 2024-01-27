package com.account.yomankum.common.exception.status4xx;

import com.account.yomankum.common.exception.BaseException;
import com.account.yomankum.web.response.ResponseCode;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }
}
