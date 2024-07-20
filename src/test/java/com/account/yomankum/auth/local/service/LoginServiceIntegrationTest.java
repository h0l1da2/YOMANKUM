package com.account.yomankum.auth.local.service;

import com.account.yomankum.auth.local.dto.request.LoginRequest;
import com.account.yomankum.auth.local.dto.response.LoginResponse;
import com.account.yomankum.common.IntegrationTest;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.user.domain.AuthInfo;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

class LoginServiceIntegrationTest extends IntegrationTest {

    @Autowired LoginService loginService;
    @Autowired UserService userService;

    private String email = "example@email.com";
    private String password = "password";
    private String nickname = "testUser";

    private User user;

    @BeforeEach
    public void setup(){
        user = newUser();
        userService.create(user);
    }

    @Test
    public void loginSuccessTest(){
        LoginRequest request = new LoginRequest(email, password);
        LoginResponse response = loginService.login(request);

        assertEquals(response.id(), user.getId());
        assertEquals(response.nickname(), user.getNickname());
    }

    @Test
    public void loginFailTest(){
        LoginRequest request = new LoginRequest(email, "wrongPassword");

        assertThrows(BadRequestException.class, () -> loginService.login(request));
    }


    private User newUser(){
        AuthInfo authInfo = AuthInfo.localUser();
        User user = User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .authInfo(authInfo)
                .build();
        return user;
    }




}

