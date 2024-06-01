package com.account.yomankum.auth.jwt.controller;

import com.account.yomankum.auth.jwt.service.RefreshTokenService;
import com.account.yomankum.common.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TokenController.class)
public class TokenControllerTest extends ControllerTest {

    @MockBean
    private RefreshTokenService refreshTokenService;

    @Test
    public void shouldReissueToken() throws Exception {
        String expectedToken = "new.jwt.token.here";
        given(refreshTokenService.reissueToken(any())).willReturn(expectedToken);

        mockMvc.perform(get("/api/v1/token/refresh"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedToken));
    }
}