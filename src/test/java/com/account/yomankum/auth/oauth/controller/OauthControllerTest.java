package com.account.yomankum.auth.oauth.controller;

import com.account.yomankum.auth.local.dto.response.LoginResponse;
import com.account.yomankum.auth.oauth.dto.OauthLoginRequest;
import com.account.yomankum.auth.oauth.dto.OauthSignupRequest;
import com.account.yomankum.auth.oauth.service.OauthService;
import com.account.yomankum.common.ControllerTest;
import com.account.yomankum.user.domain.AuthType;
import com.account.yomankum.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OauthController.class)
class OauthControllerTest extends ControllerTest {

    @MockBean
    private OauthService oauthService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    public void redirectOauthPageTest() throws Exception {
        AuthType authType = AuthType.GOOGLE;
        String expectedUrl = "http://example.com/oauth/google";

        given(oauthService.getOauthCodeRequestPageUrl(authType)).willReturn(expectedUrl);

        mockMvc.perform(get("/api/v1/oauth/" + authType.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(expectedUrl));
    }

    @Test
    public void loginTest() throws Exception {
        AuthType type = AuthType.GOOGLE;
        String code = "authCode";
        User user = User.builder().id(1L).nickname("jungnam").build();
        LoginResponse expectedResponse = LoginResponse.of("token", "refreshToken", user);
        OauthLoginRequest request = new OauthLoginRequest(type, code);

        given(oauthService.login(type, code)).willReturn(expectedResponse);

        mockMvc.perform(post("/api/v1/oauth/login")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(expectedResponse.accessToken()))
                .andExpect(jsonPath("$.refreshToken").value(expectedResponse.refreshToken()))
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.nickname").value(user.getNickname()));
    }

    @Test
    public void signup_ShouldReturnUserId() throws Exception {
        OauthSignupRequest request = new OauthSignupRequest(AuthType.GOOGLE, "googleOauthId", "user@example.com");
        Long expectedUserId = 1L;

        given(oauthService.signup(request)).willReturn(expectedUserId);

        mockMvc.perform(post("/api/v1/oauth/signup")
                        .contentType(MediaType.APPLICATION_JSON.getMediaType())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedUserId.toString()));
    }

}