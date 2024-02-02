package com.account.yomankum.user.service;

import com.account.yomankum.security.oauth.type.Tokens;

import java.util.Map;

import static com.account.yomankum.user.dto.UserDto.LoginDto;
import static com.account.yomankum.user.dto.UserDto.UserSignUpDto;

public interface UserService {
    void signUp(UserSignUpDto userSignUpDto);
    Map<Tokens, String> login(LoginDto loginDto);
}
