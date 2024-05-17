package com.account.yomankum.auth.local.controller;

import com.account.yomankum.auth.local.dto.request.LoginRequest;
import com.account.yomankum.auth.local.dto.response.LoginResponse;
import com.account.yomankum.auth.local.service.LoginService;
import com.account.yomankum.common.ControllerTest;
import com.account.yomankum.user.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
class LoginControllerTest extends ControllerTest {

    @MockBean
    private LoginService loginService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldAuthenticateUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password123");
        LoginResponse loginResponse = new LoginResponse("accessToken", "refreshToken", 1L, "nickname");

        given(loginService.login(loginRequest)).willReturn(loginResponse);

        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(loginResponse.accessToken()));
    }

}