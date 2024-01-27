package com.account.yomankum.user.controller;

import com.account.yomankum.common.exception.status4xx.UserNotFoundException;
import com.account.yomankum.user.dto.LoginDto;
import com.account.yomankum.user.service.UserService;
import com.account.yomankum.security.oauth.type.Tokens;
import com.account.yomankum.web.response.Response;
import com.account.yomankum.web.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Tag(name = "LOGIN", description = "로그인 페이지 API 명세서")
public class LoginController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "일반 회원 로그인", description = "일반 회원용 로그인")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginDto loginDto) throws UserNotFoundException {

        Map<Tokens, String> tokens = userService.login(loginDto);

        return Response.ok(ResponseCode.OK, tokens);
    }
}