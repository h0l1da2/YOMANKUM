package com.account.yomankum.login.service;

import com.account.yomankum.login.domain.LoginDto;
import com.account.yomankum.login.domain.UserSignUpDto;

import java.util.Map;

public interface UserService {
    void signUp(UserSignUpDto userSignUpDto) throws IllegalArgumentException;
    Map<String, String> login(LoginDto loginDto) throws IllegalArgumentException;
}
