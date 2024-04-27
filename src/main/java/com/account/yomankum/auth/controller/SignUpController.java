package com.account.yomankum.auth.controller;

import com.account.yomankum.auth.dto.request.UserSignUpRequest;
import com.account.yomankum.auth.service.SignUpService;
import com.account.yomankum.auth.dto.MailDto;
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

    @PostMapping("/send/mail/{mail}")
    @Operation(summary = "이메일 인증 메일 전송", description = "인증 메일 보내기")
    public void sendEmailCode(@PathVariable String mail) {
        signUpService.sendAuthCode(mail);
    }

    @PostMapping("/check/code")
    @Operation(summary = "메일 인증 코드 체크", description = "메일 인증 코드 체크")
    public boolean checkEmailCode(@RequestBody @Valid MailDto.EmailCodeDto emailCodeDto) {
        return signUpService.verifyEmailCode(emailCodeDto.email(), emailCodeDto.code());
    }

}
