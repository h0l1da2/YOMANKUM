package com.account.yomankum.security.controller;

import com.account.yomankum.security.dto.RefreshTokenReqDto;
import com.account.yomankum.security.dto.TokenResDto;
import com.account.yomankum.security.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
@Tag(name = "TOKEN", description = "토큰 API 명세서")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping
    @Operation(summary = "토큰 재발급", description = "리프레시 토큰으로 인증 후 토큰 재발급용 api")
    public TokenResDto tokenValid(@RequestBody RefreshTokenReqDto dto) {
        return tokenService.refreshTokenValid(dto);
    }
}
