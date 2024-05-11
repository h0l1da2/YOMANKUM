package com.account.yomankum.auth.local.controller;

import com.account.yomankum.auth.local.dto.request.LoginRequest;
import com.account.yomankum.auth.local.dto.response.LoginResponse;
import com.account.yomankum.auth.local.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
@Tag(name = "LOGIN", description = "로그인 페이지 API 명세서")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    @Operation(summary = "일반 회원 로그인", description = "일반 회원용 로그인")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }

}