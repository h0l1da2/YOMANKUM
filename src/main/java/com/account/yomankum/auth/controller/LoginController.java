package com.account.yomankum.auth.controller;

import com.account.yomankum.auth.Auth;
import com.account.yomankum.auth.LoginUser;
import com.account.yomankum.auth.service.LoginService;
import com.account.yomankum.user.dto.request.FirstLoginUserInfoSaveDto;
import com.account.yomankum.auth.dto.response.LoginResDto;
import com.account.yomankum.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.account.yomankum.user.dto.UserDto.UserLoginDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
@Tag(name = "LOGIN", description = "로그인 페이지 API 명세서")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    @Operation(summary = "일반 회원 로그인", description = "일반 회원용 로그인")
    public LoginResDto login(@RequestBody @Valid UserLoginDto userLoginDto) {
        return loginService.login(userLoginDto);
    }

    @PostMapping("/first")
    @Operation(summary = "첫 로그인 정보 받기", description = "첫 로그인 후 기본 정보를 저장하기 위한 창")
    public void firstLogin(@Auth LoginUser loginUser, @RequestBody @Valid FirstLoginUserInfoSaveDto firstLoginUserInfoSaveDto) {
        loginService.saveFirstLoginUserInfo(loginUser.getUserId(), firstLoginUserInfoSaveDto);
    }

}