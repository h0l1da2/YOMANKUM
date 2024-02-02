package com.account.yomankum.service.impl;

import com.account.yomankum.security.oauth.type.Tokens;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.domain.type.RoleName;
import com.account.yomankum.user.dto.UserDto.UserSignUpDto;
import com.account.yomankum.user.repository.UserRepository;
import com.account.yomankum.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.account.yomankum.user.dto.UserDto.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled
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

        User findUser = userRepository.findByEmailFetchRole(userSignUpDto.email()).orElse(null);

        boolean pwdMatches = passwordEncoder.matches(userSignUpDto.password(), findUser.getPassword());

        assertThat(findUser).isNotNull();
        assertThat(findUser.getEmail()).isEqualTo(userSignUpDto.email());
        assertThat(findUser.getRole().getRoleName()).isEqualTo(RoleName.ROLE_USER);
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

        UserLoginDto userLoginDto = getLoginDto(userSignUpDto);
        Map<Tokens, String> tokenMap = userService.login(userLoginDto);

        String accessToken = tokenMap.get(Tokens.ACCESS_TOKEN);
        String refreshToken = tokenMap.get(Tokens.REFRESH_TOKEN);

        assertThat(accessToken).isNotNull();
        assertThat(refreshToken).isNotNull();
    }

    @Test
    @DisplayName("실패 : 아이디 없는 사용자")
    void 로그인_실패_아이디없음() {

        UserLoginDto userLoginDto = UserLoginDto.builder()
                .email("username")
                .password("password")
                .build();

        assertThrows(IllegalArgumentException.class, () -> userService.login(userLoginDto));
    }

    @Test
    @DisplayName("실패 : 비밀번호 오류")
    void 로그인_실패_비밀번호오류() throws Exception {

        UserSignUpDto userSignUpDto = getUserSignUpDto();
        userService.signUp(userSignUpDto);

        UserLoginDto userLoginDto = UserLoginDto.builder()
                .email(userSignUpDto.email())
                .password("asd232112")
                .build();

        assertThrows(IllegalArgumentException.class, () -> userService.login(userLoginDto));

    }

    private UserLoginDto getLoginDto(UserSignUpDto userSignUpDto) {
        return UserLoginDto.builder()
                .email(userSignUpDto.email())
                .password(userSignUpDto.password())
                .build();
    }

    private UserSignUpDto getUserSignUpDto() {
        return UserSignUpDto.builder()
                .email("email")
                .password("password")
                .build();
    }
}