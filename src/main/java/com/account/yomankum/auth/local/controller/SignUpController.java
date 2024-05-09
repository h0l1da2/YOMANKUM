package com.account.yomankum.auth.local.controller;

import com.account.yomankum.auth.local.dto.request.UserSignUpRequest;
import com.account.yomankum.auth.local.service.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sign-up")
@Tag(name = "(NORMAL) SIGN UP", description = "일반 회원가입 API 명세서")
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping
    @Operation(summary = "일반 회원 가입", description = "일반 회원 가입 완료")
    public void signUp(@RequestBody @Valid UserSignUpRequest userSignUpDto) {
        signUpService.signUp(userSignUpDto);
    }

    @PostMapping("/send/mail/{email}")
    @Operation(summary = "이메일 인증 메일 전송", description = "인증 메일 보내기")
    public void sendEmailCode(@PathVariable String email) {
        signUpService.sendAuthCodeMail(email);
    }

    @PostMapping("/check/code/{email}/{code}")
    @Operation(summary = "메일 인증 코드 체크", description = "메일 인증 코드 체크")
    public boolean checkEmailCode(@PathVariable String email, @PathVariable String code) {
        return signUpService.verifyEmailCode(email, code);
    }

}
