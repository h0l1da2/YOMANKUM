package com.account.yomankum.auth.jwt.controller;

import com.account.yomankum.auth.jwt.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
@Tag(name = "Token")
public class TokenController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping("/refresh")
    @Operation(summary = "토큰 재발급")
    public String reissueToken(HttpServletRequest request){
        String result = refreshTokenService.reissueToken(request);
        return result;
    }
}
