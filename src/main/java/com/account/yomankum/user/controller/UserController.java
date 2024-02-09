package com.account.yomankum.user.controller;

import com.account.yomankum.security.service.CustomUserDetails;
import com.account.yomankum.user.dto.request.UserInfoUpdateDto;
import com.account.yomankum.user.dto.response.UserInfoDto;
import com.account.yomankum.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "USER", description = "유저 페이지 API 명세서")
public class UserController {

    private final UserService userService;

    @GetMapping("my-page")
    @Operation(summary = "마이 페이지", description = "마이 페이지 정보 가져오기")
    public UserInfoDto myPage(HttpServletRequest request) {
        return userService.getUserInfo(request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    @PutMapping("my-page")
    @Operation(summary = "마이 페이지", description = "마이 페이지 정보 가져오기")
    public void updateMyPage(@AuthenticationPrincipal Principal principal, @RequestBody UserInfoUpdateDto dto) {
        userService.updateUserInfo((CustomUserDetails) principal, dto);
    }


    @PutMapping("/password/{uuid}")
    @Operation(summary = "패스워드 변경", description = "패스워드 변경하기")
    public void updatePassword(@PathVariable String uuid, @RequestBody String password) {
        userService.updatePassword(uuid, password);
    }
}
