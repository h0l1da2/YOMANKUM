package com.account.yomankum.controller;

import com.account.yomankum.domain.dto.LoginDto;
import com.account.yomankum.exception.IncorrectLoginException;
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

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Response> loginMain() {
        return Response.ok();
    }

    @PostMapping
    public ResponseEntity<Response> login(@Valid LoginDto loginDto) throws UserDuplicateException, IncorrectLoginException {

        Map<String, String> tokens = userService.login(loginDto);

        return Response.ok(tokens);
    }
}