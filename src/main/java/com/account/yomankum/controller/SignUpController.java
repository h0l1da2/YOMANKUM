package com.account.yomankum.controller;

import com.account.yomankum.domain.Mail;
import com.account.yomankum.domain.dto.EmailCodeDto;
import com.account.yomankum.domain.dto.EmailDto;
import com.account.yomankum.domain.dto.UserSignUpDto;
import com.account.yomankum.service.MailService;
import com.account.yomankum.service.UserService;
import com.account.yomankum.web.Response;
import com.account.yomankum.web.ResponseCode;
import com.sun.mail.smtp.SMTPAddressFailedException;
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
public class SignUpController {

    private final UserService userService;
    private final MailService mailService;

    @GetMapping
    public ResponseEntity<Response> signUpMain() {
        return Response.ok();
    }

    @PostMapping
    public ResponseEntity<Response> signUp(@RequestBody @Valid UserSignUpDto userSignUpDto) {

        // 회원가입
        userService.signUp(userSignUpDto);

        return Response.ok(ResponseCode.SIGNUP000);
    }

    @PostMapping("/email/send")
    public ResponseEntity<Response> sendEmailCode(@RequestBody @Valid EmailDto emailDto) {

        String code = null;
        try {
            code = mailService.mailSend(Mail.JOIN, emailDto.email());
        } catch (SMTPAddressFailedException e) {
            log.error("메일을 보낼 수 없음");
            e.printStackTrace();
            return Response.badRequest(ResponseCode.EMAIL002);
        } catch (MessagingException e) {
            log.error("메시지 에러");
            e.printStackTrace();
            return Response.badRequest(ResponseCode.EMAIL004);
        }

        EmailCodeDto emailCodeDto =
                EmailCodeDto.builder()
                .email(emailDto.email())
                .code(code)
                .build();

        log.info("이메일 코드 전송 : {}", emailDto.email());

        return Response.ok(
                ResponseCode.EMAIL000, emailCodeDto);
    }

    @PostMapping("/email/check")
    public ResponseEntity<Response> checkEmailCode(@RequestBody @Valid EmailCodeDto emailCodeDto) {

        boolean isSuccessMailCode = mailService.verifyEmailCode(emailCodeDto.email(), emailCodeDto.code());

        if (!isSuccessMailCode) {
            log.error("입력한 코드가 일치하지 않음 : {}", emailCodeDto.code());
            return Response.badRequest(ResponseCode.EMAIL004);
        }

        return Response.ok(ResponseCode.EMAIL000);
    }
}
