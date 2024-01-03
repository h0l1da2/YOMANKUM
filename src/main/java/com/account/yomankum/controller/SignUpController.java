package com.account.yomankum.controller;

import com.account.yomankum.domain.Mail;
import com.account.yomankum.domain.dto.EmailCodeDto;
import com.account.yomankum.domain.dto.EmailDto;
import com.account.yomankum.domain.dto.UserSignUpDto;
import com.account.yomankum.exception.UserDuplicateException;
import com.account.yomankum.service.MailService;
import com.account.yomankum.service.UserService;
import com.account.yomankum.web.Response;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signUp")
public class SignUpController {

    private final UserService userService;
    private final MailService mailService;

    @GetMapping
    public ResponseEntity<Response> signUpMain() {
        return Response.ok();
    }

    @PostMapping
    public ResponseEntity<Response> signUp(@RequestBody @Valid UserSignUpDto userSignUpDto) throws UserDuplicateException {

        // 회원가입
        userService.signUp(userSignUpDto);

        return Response.ok();
    }

    @PostMapping("/email/send")
    public ResponseEntity<Response> sendEmailCode(@RequestBody @Valid EmailDto emailDto) throws MessagingException {

        String code = mailService.mailSend(Mail.JOIN, emailDto.email());

        EmailCodeDto emailCodeDto =
                EmailCodeDto.builder()
                .email(emailDto.email())
                .code(code)
                .build();

        return Response.ok(emailCodeDto);
    }

    @PostMapping("/email/check")
    public ResponseEntity<Response> checkEmailCode(@RequestBody @Valid EmailCodeDto emailCodeDto) {

        boolean isSuccessMailCode = mailService.verifyEmailCode(emailCodeDto.email(), emailCodeDto.code());

        if (!isSuccessMailCode) {
            return Response.badRequest("메일 코드가 다름");
        }

        return Response.ok();
    }
}
