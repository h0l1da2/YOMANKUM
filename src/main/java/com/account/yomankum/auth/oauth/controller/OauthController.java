package com.account.yomankum.auth.oauth.controller;

import com.account.yomankum.auth.local.dto.response.LoginResponse;
import com.account.yomankum.auth.oauth.dto.OauthSignupRequest;
import com.account.yomankum.auth.oauth.service.OauthService;
import com.account.yomankum.user.domain.AuthType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/{oauthType}")
    public ResponseEntity<Void> redirectToOauthCodeRequestPage(@PathVariable AuthType oauthType, HttpServletResponse response) throws IOException {
        String pageUrl = oauthService.getOauthCodeRequestPageUrl(oauthType);
        response.sendRedirect(pageUrl);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public LoginResponse login(AuthType type, String code){
        return oauthService.login(type, code);
    }

    @PostMapping("/signup")
    public Long signup(@RequestBody OauthSignupRequest request){
        return oauthService.signup(request);
    }
}
