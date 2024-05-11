package com.account.yomankum.auth.jwt.controller;

import com.account.yomankum.auth.jwt.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class TokenController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping("/refresh")
    public String reissueToken(HttpServletRequest request){
        return refreshTokenService.reissueToken(request);
    }
}
