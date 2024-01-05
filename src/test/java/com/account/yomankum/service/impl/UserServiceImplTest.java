package com.account.yomankum.service.impl;

import com.account.yomankum.domain.enums.Name;
import com.account.yomankum.domain.User;
import com.account.yomankum.login.domain.LoginDto;
import com.account.yomankum.login.domain.UserSignUpDto;
import com.account.yomankum.repository.UserRepository;
import com.account.yomankum.login.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("성공 : 회원가입 성공")
    void 회원가입_성공() throws Exception {
        UserSignUpDto userSignUpDto = getUserSignUpDto();

        userService.signUp(userSignUpDto);

        User findUser = userRepository.findByEmailFetchRole(userSignUpDto.getEmail()).orElse(null);

        boolean pwdMatches = passwordEncoder.matches(userSignUpDto.getPassword(), findUser.getPassword());

        assertThat(findUser).isNotNull();
        assertThat(findUser.getEmail()).isEqualTo(userSignUpDto.getEmail());
        assertThat(findUser.getRole().getName()).isEqualTo(Name.ROLE_USER);
        assertThat(pwdMatches).isTrue();
    }

    @Test
    @DisplayName("실패 : 중복 회원")
    void 회원가입_실패_중복회원() throws Exception {


        UserSignUpDto userSignUpDtoA = getUserSignUpDto();
        UserSignUpDto userSignUpDtoB = getUserSignUpDto();

        userService.signUp(userSignUpDtoA);
        assertThrows(IllegalArgumentException.class, () -> userService.signUp(userSignUpDtoB));

    }

    @Test
    @DisplayName("성공 : 로그인")
    void 로그인_성공() throws Exception {

        UserSignUpDto userSignUpDto = getUserSignUpDto();
        userService.signUp(userSignUpDto);

        LoginDto loginDto = getLoginDto(userSignUpDto);
        Map<String, String> tokenMap = userService.login(loginDto);

        String accessToken = tokenMap.get("accessToken");
        String refreshToken = tokenMap.get("refreshToken");

        assertThat(accessToken).isNotNull();
        assertThat(refreshToken).isNotNull();
    }

    @Test
    @DisplayName("실패 : 아이디 없는 사용자")
    void 로그인_실패_아이디없음() {

        LoginDto loginDto = LoginDto.builder()
                .email("username")
                .password("password")
                .build();

        assertThrows(IllegalArgumentException.class, () -> userService.login(loginDto));
    }

    @Test
    @DisplayName("실패 : 비밀번호 오류")
    void 로그인_실패_비밀번호오류() throws Exception {

        UserSignUpDto userSignUpDto = getUserSignUpDto();
        userService.signUp(userSignUpDto);

        LoginDto loginDto = LoginDto.builder()
                .email(userSignUpDto.getEmail())
                .password("asd232112")
                .build();

        assertThrows(IllegalArgumentException.class, () -> userService.login(loginDto));

    }

    private LoginDto getLoginDto(UserSignUpDto userSignUpDto) {
        return LoginDto.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .build();
    }

    private UserSignUpDto getUserSignUpDto() {
        return UserSignUpDto.builder()
                .email("email")
                .password("password")
                .build();
    }
}