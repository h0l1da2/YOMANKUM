package com.account.yomankum.user.controller;

import com.account.yomankum.auth.common.AuthArgumentResolver;
import com.account.yomankum.auth.common.LoginUser;
import com.account.yomankum.auth.jwt.service.TokenService;
import com.account.yomankum.auth.local.controller.LoginController;
import com.account.yomankum.common.AbstractRestDocsTests;
import com.account.yomankum.user.domain.Gender;
import com.account.yomankum.user.dto.request.UserInfoUpdateRequest;
import com.account.yomankum.user.dto.response.UserInfoDto;
import com.account.yomankum.user.service.UserFinder;
import com.account.yomankum.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends AbstractRestDocsTests {

    @Autowired private ObjectMapper objectMapper;
    @MockBean private UserService userService;
    @MockBean private UserFinder userFinder;
    @MockBean private TokenService tokenService;

    private final Long userId = 1L;

    @BeforeEach
    void setUp() {
        given(tokenService.getUserId(any())).willReturn(userId);
    }

    @Test
    public void myPageInfoTest() throws Exception {
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .nickname("John Doe")
                .email("john@example.com")
                .build();
        given(userFinder.getUserInfo(userId)).willReturn(userInfoDto);

        mockMvc.perform(get("/api/v1/user/info")
                        .contentType(MediaType.APPLICATION_JSON.getMediaType()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(userInfoDto.nickname()))
                .andExpect(jsonPath("$.email").value(userInfoDto.email()));
    }

    @Test
    public void updateUserInfoTest() throws Exception {
        UserInfoUpdateRequest updateRequest = UserInfoUpdateRequest.builder()
                .nickname("John Doe")
                .gender(Gender.MALE)
                .build();

        mockMvc.perform(put("/api/v1/user/info")
                        .contentType(MediaType.APPLICATION_JSON.getMediaType())
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        verify(userService).updateUserInfo(eq(userId), any(UserInfoUpdateRequest.class));
    }

}