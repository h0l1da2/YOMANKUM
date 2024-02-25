package com.account.yomankum.user.controller;

import com.account.yomankum.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.account.yomankum.user.dto.UserDto.UserSignUpDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sign-up")
@Tag(name = "(NORMAL) SIGN UP", description = "일반 회원가입 API 명세서")
public class SignUpController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "일반 회원 가입", description = "일반 회원 가입 완료")
    public void signUp(@RequestBody @Valid UserSignUpDto userSignUpDto) {
        userService.signUp(userSignUpDto);
    }

}
