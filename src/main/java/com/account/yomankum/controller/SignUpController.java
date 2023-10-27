package com.account.yomankum.controller;

import com.account.yomankum.domain.dto.UserSignUpDto;
import com.account.yomankum.service.SignUpService;
import com.account.yomankum.web.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signUp")
public class SignUpController {

    private final SignUpService signUpService;

    @GetMapping
    public ResponseEntity<Response> signUpMain(@Valid UserSignUpDto userSignUpDto) {

        // 회원가입
        signUpService.signUp(userSignUpDto);

        return Response.ok();
    }
}
