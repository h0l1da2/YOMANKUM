package com.account.yomankum.auth.oauth.controller;

import com.account.yomankum.auth.local.dto.response.LoginResponse;
import com.account.yomankum.auth.oauth.dto.OauthSignupRequest;
import com.account.yomankum.auth.oauth.service.OauthService;
import com.account.yomankum.user.domain.AuthType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
@Tag(name = "Oauth", description = "Oauth 관련 api")
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/{oauthType}")
    @Operation(summary = "oauth 페이지로 이동")
    public ResponseEntity<Void> redirectToOauthCodeRequestPage(@PathVariable AuthType oauthType, HttpServletResponse response) throws IOException {
        String pageUrl = oauthService.getOauthCodeRequestPageUrl(oauthType);
        response.sendRedirect(pageUrl);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(summary = "oauth 로그인")
    public LoginResponse login(AuthType type, String code){
        return oauthService.login(type, code);
    }

    @PostMapping("/signup")
    @Operation(summary = "oauth 계정으로 회원가입")
    public Long signup(@RequestBody OauthSignupRequest request){
        return oauthService.signup(request);
    }
}
