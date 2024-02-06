package com.account.yomankum.user.controller;

import com.account.yomankum.user.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.account.yomankum.user.dto.MailDto.EmailCodeDto;
import static com.account.yomankum.user.dto.MailDto.EmailRequestDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
@Tag(name = "(NORMAL) EMAIL", description = "이메일 API 명세서")
public class EmailController {

    private final MailService mailService;

    @PostMapping
    @Operation(summary = "인증 메일 보내기", description = "인증 메일 보내기")
    public void sendEmailCode(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        mailService.mailSend(emailRequestDto);
    }

    @PostMapping("/code/check")
    @Operation(summary = "메일 인증 코드 체크", description = "메일 인증 코드 체크")
    public void checkEmailCode(@RequestBody @Valid EmailCodeDto emailCodeDto) {
        mailService.verifyEmailCode(emailCodeDto.email(), emailCodeDto.code());
    }

    // 비밀번호 재설정 메일 보내기

}
