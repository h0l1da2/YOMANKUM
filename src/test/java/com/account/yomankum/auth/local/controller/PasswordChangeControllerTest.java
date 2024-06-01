package com.account.yomankum.auth.local.controller;

import com.account.yomankum.auth.local.dto.request.PasswordChangeRequest;
import com.account.yomankum.auth.local.service.PasswordChangeService;
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

@WebMvcTest(PasswordChangeController.class)
public class PasswordChangeControllerTest extends ControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PasswordChangeService passwordChangeService;

    @Test
    public void shouldSendEmailCode() throws Exception {
        String email = "user@example.com";

        mockMvc.perform(post("/api/v1/password/send/mail/" + email))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCheckEmailCode() throws Exception {
        String email = "user@example.com";
        String code = "123456";

        given(passwordChangeService.isValidCode(email, code)).willReturn(true);

        mockMvc.perform(get("/api/v1/password/check/code/" + email + "/" + code))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldChangePassword() throws Exception {
        PasswordChangeRequest request = new PasswordChangeRequest("user@example.com", "123456", "newPassword123");

        mockMvc.perform(put("/api/v1/password/user@example.com/123456")
                        .contentType(MediaType.APPLICATION_JSON.getMediaType())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}