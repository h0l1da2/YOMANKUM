package com.account.yomankum.user.controller;

import com.account.yomankum.user.dto.response.UserInfoDto;
import com.account.yomankum.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "USER", description = "유저 페이지 API 명세서")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "마이 페이지", description = "마이 페이지 정보 가져오기")
    public UserInfoDto myPage(HttpServletRequest request) {
        return userService.userInfo(request.getHeader(HttpHeaders.AUTHORIZATION));
    }

}
