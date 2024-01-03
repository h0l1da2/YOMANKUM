package com.account.yomankum.controller;

import com.account.yomankum.domain.dto.LoginDto;
import com.account.yomankum.service.UserService;
import com.account.yomankum.web.Response;
import com.account.yomankum.web.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
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
    public ResponseEntity<Response> login(@RequestBody @Valid LoginDto loginDto) {

        Map<String, String> tokens = null;
        try {
            tokens = userService.login(loginDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.badRequest(ResponseCode.LOGIN004);
        }

        return Response.ok(ResponseCode.LOGIN000, tokens);
    }
}