package com.account.yomankum.user.controller;

import com.account.yomankum.auth.common.Auth;
import com.account.yomankum.auth.common.LoginUser;
import com.account.yomankum.user.dto.request.UserInfoUpdateRequest;
import com.account.yomankum.user.dto.response.UserInfoDto;
import com.account.yomankum.user.service.UserFinder;
import com.account.yomankum.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "유저 페이지 API 명세서")
public class UserController {

    private final UserService userService;
    private final UserFinder userFinder;

    @GetMapping("/info")
    @Operation(summary = "유저 정보", description = "유저 정보 가져오기")
    public UserInfoDto myPage(@Auth LoginUser loginUser) {
        return userFinder.getUserInfo(loginUser.getUserId());
    }

    @PutMapping("/info")
    @Operation(summary = "유저 정보 수정", description = "유저 정보 수정")
    public void updateMyPage(@Auth LoginUser loginUser, @RequestBody @Valid UserInfoUpdateRequest request) {
        userService.updateUserInfo(loginUser.getUserId(), request);
    }

    @PutMapping("/password")
    @Operation(summary = "패스워드 변경", description = "로그인 상태에서 패스워드 변경하기")
    public void updatePassword(@Auth LoginUser loginUser, @RequestBody String password) {
        userService.updatePasswordByUserId(loginUser.getUserId(), password);
    }
}
