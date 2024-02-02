package com.account.yomankum.user.controller;

import com.account.yomankum.user.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.account.yomankum.user.dto.MailDto.EmailCodeDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
@Tag(name = "(NORMAL) EMAIL", description = "이메일 API 명세서")
public class EmailController {

    private final MailService mailService;

    @GetMapping
    @Operation(summary = "인증 메일 보내기", description = "인증 메일 보내기")
    public String sendEmailCode(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "type", defaultValue = "JOIN") String mailType
    ) {
        return mailService.mailSend(mailType, email);
    }

    @PostMapping("/code/check")
    @Operation(summary = "메일 인증 코드 체크", description = "메일 인증 코드 체크")
    public void checkEmailCode(@RequestBody @Valid EmailCodeDto emailCodeDto) {
        mailService.verifyEmailCode(emailCodeDto.email(), emailCodeDto.code());
    }

}
