package com.account.yomankum.service;

import com.account.yomankum.domain.dto.UserSignUpDto;
import com.account.yomankum.exception.UserDuplicateException;

public interface SignUpService {
    void signUp(UserSignUpDto userSignUpDto) throws UserDuplicateException;
}
