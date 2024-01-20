package com.account.yomankum.login.service;

import com.account.yomankum.exception.UserNotFoundException;
import com.account.yomankum.login.domain.LoginDto;
import com.account.yomankum.login.domain.UserSignUpDto;
import com.account.yomankum.security.domain.type.Tokens;

import java.util.Map;

public interface UserService {
    void signUp(UserSignUpDto userSignUpDto) throws IllegalArgumentException, UserNotFoundException;
    Map<Tokens, String> login(LoginDto loginDto) throws IllegalArgumentException, UserNotFoundException;
}
