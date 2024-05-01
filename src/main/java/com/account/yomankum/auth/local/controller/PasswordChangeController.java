package com.account.yomankum.auth.local.controller;

import com.account.yomankum.auth.local.dto.MailDto;
import com.account.yomankum.auth.local.dto.request.PasswordChangeRequest;
import com.account.yomankum.auth.local.service.PasswordChangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/password")
@Tag(name = "비밀번호 변경", description = "로그인하지 않은 유저가 비밀번호 변경")
public class PasswordChangeController {

    private final PasswordChangeService passwordChangeService;

    @PostMapping("/send/mail/{email}")
    @Operation(summary = "이메일 인증 메일 전송", description = "인증 메일 보내기")
    public void sendEmailCode(@PathVariable String email) {
        passwordChangeService.sendAuthCodeMail(email);
    }

    @PostMapping("/check/code")
    @Operation(summary = "메일 인증 코드 체크", description = "메일 인증 코드 체크")
    public boolean checkEmailCode(@RequestBody @Valid MailDto.EmailCodeDto emailCodeDto) {
        return passwordChangeService.isValidCode(emailCodeDto.email(), emailCodeDto.code());
    }

    @PutMapping("/{email}/{code}")
    @Operation(description = "비밀번호 변경")
    public void changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest){
        passwordChangeService.updatePassword(passwordChangeRequest);
    }

}
