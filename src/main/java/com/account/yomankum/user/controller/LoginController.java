package com.account.yomankum.user.controller;

import com.account.yomankum.security.oauth.type.Tokens;
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

import java.util.Map;

import static com.account.yomankum.user.dto.UserDto.UserLoginDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
@Tag(name = "LOGIN", description = "로그인 페이지 API 명세서")
public class LoginController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "일반 회원 로그인", description = "일반 회원용 로그인")
    public Map<Tokens, String> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }
}