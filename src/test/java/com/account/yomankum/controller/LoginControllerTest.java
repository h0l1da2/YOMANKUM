package com.account.yomankum.controller;

import com.account.yomankum.user.repository.UserRepository;
import com.account.yomankum.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.account.yomankum.user.dto.UserDto.LoginDto;
import static com.account.yomankum.user.dto.UserDto.UserSignUpDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    void cleanAll() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void 로그인_성공() throws Exception {

        UserSignUpDto userSignUpDto = UserSignUpDto.builder()
                .email("email@naver.com")
                .password("123456a")
                .build();

        userService.signUp(userSignUpDto);

        LoginDto loginDto = LoginDto.builder()
                .email(userSignUpDto.email())
                .password(userSignUpDto.password())
                .build();

        mockMvc.perform(
                        post("/login")
                                .content(mapper.writeValueAsString(loginDto))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패 : 가입 안 된 이메일")
    void 로그인_실패_가입X() throws Exception {

        UserSignUpDto userSignUpDto = UserSignUpDto.builder()
                .email("email@naver.com")
                .password("123456a")
                .build();

        LoginDto loginDto = LoginDto.builder()
                .email(userSignUpDto.email())
                .password(userSignUpDto.password())
                .build();

        mockMvc.perform(
                        post("/login")
                                .content(mapper.writeValueAsString(loginDto))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError())
                .andDo(print());
    }

}