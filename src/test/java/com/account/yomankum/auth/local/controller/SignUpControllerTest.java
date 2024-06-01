package com.account.yomankum.auth.local.controller;

import com.account.yomankum.auth.local.dto.request.UserSignUpRequest;
import com.account.yomankum.auth.local.service.SignUpService;
import com.account.yomankum.common.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
public class SignUpControllerTest extends ControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SignUpService signUpService;

    @Test
    public void shouldSignUpUser() throws Exception {
        UserSignUpRequest signUpRequest = new UserSignUpRequest("user@example.com", "Password123!");

        mockMvc.perform(post("/api/v1/sign-up")
                        .contentType(MediaType.APPLICATION_JSON.getMediaType())
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSendEmailCode() throws Exception {
        String email = "user@example.com";

        mockMvc.perform(post("/api/v1/sign-up/send/mail/" + email))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldVerifyEmailCode() throws Exception {
        String email = "user@example.com";
        String code = "123456";

        given(signUpService.verifyEmailCode(email, code)).willReturn(true);

        mockMvc.perform(get("/api/v1/sign-up/check/code/" + email + "/" + code))
                .andExpect(status().isOk());
    }
}