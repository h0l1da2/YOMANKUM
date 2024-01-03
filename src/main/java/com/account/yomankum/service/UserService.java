package com.account.yomankum.service;

import com.account.yomankum.domain.dto.LoginDto;
import com.account.yomankum.domain.dto.UserSignUpDto;

import java.util.Map;

public interface UserService {
    void signUp(UserSignUpDto userSignUpDto) throws IllegalArgumentException;
    Map<String, String> login(LoginDto loginDto) throws IllegalArgumentException;
}
