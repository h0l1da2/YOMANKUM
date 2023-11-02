package com.account.yomankum.service;

import com.account.yomankum.domain.dto.LoginDto;
import com.account.yomankum.domain.dto.UserSignUpDto;
import com.account.yomankum.exception.IncorrectLoginException;
import com.account.yomankum.exception.UserDuplicateException;

import java.util.Map;

public interface UserService {
    void signUp(UserSignUpDto userSignUpDto) throws UserDuplicateException;
    Map<String, String> login(LoginDto loginDto) throws UserDuplicateException, IncorrectLoginException;
}
