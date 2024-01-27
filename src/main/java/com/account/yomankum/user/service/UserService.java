package com.account.yomankum.user.service;

import com.account.yomankum.common.exception.status4xx.UserNotFoundException;
import com.account.yomankum.user.dto.LoginDto;
import com.account.yomankum.user.dto.UserSignUpDto;
import com.account.yomankum.security.oauth.type.Tokens;

import java.util.Map;

public interface UserService {
    void signUp(UserSignUpDto userSignUpDto) throws IllegalArgumentException, UserNotFoundException;
    Map<Tokens, String> login(LoginDto loginDto) throws IllegalArgumentException, UserNotFoundException;
}
