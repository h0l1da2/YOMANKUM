package com.account.yomankum.user.service;

import com.account.yomankum.security.service.CustomUserDetails;
import com.account.yomankum.user.dto.request.FirstLoginUserInfoSaveDto;
import com.account.yomankum.user.dto.request.UserInfoUpdateDto;
import com.account.yomankum.user.dto.response.LoginResDto;
import com.account.yomankum.user.dto.response.UserInfoDto;

import static com.account.yomankum.user.dto.UserDto.UserLoginDto;
import static com.account.yomankum.user.dto.UserDto.UserSignUpDto;

public interface UserService {
    void signUp(UserSignUpDto userSignUpDto);
    LoginResDto login(UserLoginDto userLoginDto);
    UserInfoDto getUserInfo(CustomUserDetails userDetails);
    void updatePassword(String uuid, String password);
    void saveFirstLoginUserInfo(FirstLoginUserInfoSaveDto firstLoginUserInfoSaveDto);
    void updateUserInfo(CustomUserDetails userDetails, UserInfoUpdateDto dto);
}
