package com.account.yomankum.user.controller;

import com.account.yomankum.user.domain.type.Mail;
import com.account.yomankum.user.dto.MailDto.EmailDto;
import com.account.yomankum.user.service.MailService;
import com.account.yomankum.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.account.yomankum.user.dto.MailDto.EmailCodeDto;
import static com.account.yomankum.user.dto.UserDto.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sign-up")
@Tag(name = "(NORMAL) SIGN UP", description = "일반 회원가입 API 명세서")
public class SignUpController {

    private final UserService userService;
    private final MailService mailService;

    @PostMapping
    @Operation(summary = "일반 회원 가입", description = "일반 회원 가입 완료")
    public void signUp(@RequestBody @Valid UserSignUpDto userSignUpDto) {
        userService.signUp(userSignUpDto);
    }

    @PostMapping("/email/send")
    @Operation(summary = "인증 메일 보내기", description = "회원가입용 인증 메일 보내기")
    public String sendEmailCode(@RequestBody @Valid EmailDto emailDto) throws MessagingException {
        return mailService.mailSend(Mail.JOIN, emailDto.email());
    }

    @PostMapping("/email/check")
    @Operation(summary = "메일 인증 코드 체크", description = "메일 인증 코드 체크")
    public void checkEmailCode(@RequestBody @Valid EmailCodeDto emailCodeDto) {
        mailService.verifyEmailCode(emailCodeDto.email(), emailCodeDto.code());
    }
}
