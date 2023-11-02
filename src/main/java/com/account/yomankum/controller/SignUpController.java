package com.account.yomankum.controller;

import com.account.yomankum.domain.dto.UserSignUpDto;
import com.account.yomankum.exception.UserDuplicateException;
import com.account.yomankum.service.UserService;
import com.account.yomankum.web.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signUp")
public class SignUpController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Response> signUpMain() {
        return Response.ok();
    }

    @PostMapping
    public ResponseEntity<Response> signUp(@Valid UserSignUpDto userSignUpDto) throws UserDuplicateException {

        // 회원가입
        userService.signUp(userSignUpDto);

        return Response.ok();
    }
}
