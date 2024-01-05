package com.account.yomankum.controller;

import com.account.yomankum.login.domain.EmailCodeDto;
import com.account.yomankum.login.domain.EmailDto;
import com.account.yomankum.login.domain.UserSignUpDto;
import com.account.yomankum.repository.UserRepository;
import com.account.yomankum.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisUtil redisUtil;

    @BeforeEach
    void cleanAll() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공 : 성공")
    void 회원가입_성공() throws Exception {

        UserSignUpDto userSignUpDto = getUserSignUpDto();
        mockMvc.perform(
                post("/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userSignUpDto))
        ).andExpect(status().is2xxSuccessful())
                .andDo(print());

    }

    @Test
    @DisplayName("회원가입 실패 : 아이디 NULL")
    void 회원가입_실패_아이디_NULL() throws Exception {

        UserSignUpDto userSignUpDto = UserSignUpDto.builder()
                .password("password").build();

        mockMvc.perform(
                post("/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userSignUpDto))
        ).andExpect(status().is4xxClientError())
                .andDo(print());

    }

    @Test
    @DisplayName("회원가입 실패 : 비밀번호 NULL")
    void 회원가입_실패_비밀번호_NULL() throws Exception {

        UserSignUpDto userSignUpDto = UserSignUpDto.builder()
                .email("email@naver.com").build();

        mockMvc.perform(
                post("/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userSignUpDto))
        ).andExpect(status().is4xxClientError())
                .andDo(print());

    }

    @Test
    @DisplayName("회원가입 실패 : 이메일 형식 이상")
    void 회원가입_실패_이메일형식_FAIL() throws Exception {

        UserSignUpDto userSignUpDto = UserSignUpDto.builder()
                .email("email").password("1234aaaa").build();

        mockMvc.perform(
                post("/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userSignUpDto))
        ).andExpect(status().is4xxClientError())
                .andDo(print());

    }

    @Test
    @DisplayName("회원가입 실패 : 비밀번호 5자 이하")
    void 회원가입_실패_비밀번호_5자이하() throws Exception {

        UserSignUpDto userSignUpDto = UserSignUpDto.builder()
                .email("email@naver.com").password("12345").build();

        mockMvc.perform(
                post("/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userSignUpDto))
        ).andExpect(status().is4xxClientError())
                .andDo(print());

    }

    @Test
    @DisplayName("회원가입 성공 : 비밀번호 6자 이상")
    void 회원가입_성공_비밀번호_6자이상() throws Exception {

        UserSignUpDto userSignUpDto = UserSignUpDto.builder()
                .email("email@naver.com").password("123456").build();

        mockMvc.perform(
                post("/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userSignUpDto))
        ).andExpect(status().is2xxSuccessful())
                .andDo(print());

    }

    @Test
    @DisplayName("메일 보내기 성공")
    void 메일_보내기_성공() throws Exception {

        EmailDto emailDto = getEmailDto();

        mockMvc.perform(
                post("/signUp/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(emailDto))
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.email").value(emailDto.email()))
                .andDo(print());

    }

    @Test
    @DisplayName("메일 보내기 실패 : 형식 이상")
    void 메일_보내기_실패_형식_이상() throws Exception {

        EmailDto emailDto = EmailDto.builder()
                .email("holicloud.com")
                .build();

        mockMvc.perform(
                post("/signUp/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(emailDto))
        ).andExpect(status().is4xxClientError())
                .andDo(print());

    }

    @Test
    @DisplayName("이메일 코드 확인 성공")
    void 이메일_코드_확인_후_성공() throws Exception {

        EmailDto emailDto = getEmailDto();

        mockMvc.perform(
                post("/signUp/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(emailDto))
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.email").value(emailDto.email()))
                .andExpect(jsonPath("$.data.code")
                        .value(redisUtil.getData(emailDto.email())))
                .andExpect(
                        mvc -> mockMvc.perform(post("/signUp/email/check")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(EmailCodeDto.builder()
                                        .email(emailDto.email())
                                        .code(redisUtil.getData(emailDto.email()))
                                        .build())))
                                .andExpect(status().is2xxSuccessful())
                                .andDo(print())
                )
                .andDo(print());

    }

    private EmailDto getEmailDto() {
        return EmailDto.builder()
                .email("holiday.k1@icloud.com")
                .build();
    }

    private UserSignUpDto getUserSignUpDto() {
        return UserSignUpDto.builder()
                .email("email@naver.com")
                .password("password")
                .build();
    }
}