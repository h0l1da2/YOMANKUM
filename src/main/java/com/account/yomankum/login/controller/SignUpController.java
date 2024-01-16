package com.account.yomankum.login.controller;

import com.account.yomankum.domain.enums.Mail;
import com.account.yomankum.exception.CodeNotFoundException;
import com.account.yomankum.exception.CodeNotValidException;
import com.account.yomankum.exception.UserNotFoundException;
import com.account.yomankum.login.domain.EmailCodeDto;
import com.account.yomankum.login.domain.EmailDto;
import com.account.yomankum.login.domain.UserSignUpDto;
import com.account.yomankum.login.service.MailService;
import com.account.yomankum.login.service.UserService;
import com.account.yomankum.web.response.Response;
import com.account.yomankum.web.response.ResponseCode;
import com.sun.mail.smtp.SMTPAddressFailedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/signUp")
@Tag(name = "(NORMAL) SIGN UP", description = "일반 회원가입 API 명세서")
public class SignUpController {

    private final UserService userService;
    private final MailService mailService;

    @PostMapping
    @Operation(summary = "일반 회원 가입", description = "일반 회원 가입 완료")
    public ResponseEntity<Response> signUp(@RequestBody @Valid UserSignUpDto userSignUpDto) throws UserNotFoundException {

        // 회원가입
        userService.signUp(userSignUpDto);

        return Response.ok(ResponseCode.OK);
    }

    @PostMapping("/email/send")
    @Operation(summary = "인증 메일 보내기", description = "회원가입용 인증 메일 보내기")
    public ResponseEntity<Response> sendEmailCode(@RequestBody @Valid EmailDto emailDto) throws MessagingException {

        String code = mailService.mailSend(Mail.JOIN, emailDto.email());

        return Response.ok(
                ResponseCode.OK, code);
    }

    @PostMapping("/email/check")
    @Operation(summary = "메일 인증 코드 체크", description = "메일 인증 코드 체크")
    public ResponseEntity<Response> checkEmailCode(@RequestBody @Valid EmailCodeDto emailCodeDto) throws CodeNotFoundException, CodeNotValidException {

        mailService.verifyEmailCode(emailCodeDto.email(), emailCodeDto.code());

        return Response.ok(ResponseCode.OK);
    }
}
