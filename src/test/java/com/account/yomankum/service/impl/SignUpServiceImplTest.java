package com.account.yomankum.service.impl;

import com.account.yomankum.domain.User;
import com.account.yomankum.domain.dto.UserSignUpDto;
import com.account.yomankum.exception.UserDuplicateException;
import com.account.yomankum.repository.UserRepository;
import com.account.yomankum.service.SignUpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class SignUpServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SignUpService signUpService;
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

        signUpService.signUp(userSignUpDto);

        User findUser = userRepository.findByUsername(userSignUpDto.getUsername()).orElse(null);

        boolean pwdMatches = passwordEncoder.matches(userSignUpDto.getPassword(), findUser.getPassword());

        assertThat(findUser).isNotNull();
        assertThat(findUser.getUsername()).isEqualTo(userSignUpDto.getUsername());
        assertThat(pwdMatches).isTrue();
    }

    @Test
    @DisplayName("실패 : 중복 회원")
    void 회원가입_실패_중복회원() throws Exception {


        UserSignUpDto userSignUpDtoA = getUserSignUpDto();
        UserSignUpDto userSignUpDtoB = getUserSignUpDto();

        signUpService.signUp(userSignUpDtoA);
        assertThrows(UserDuplicateException.class, () -> signUpService.signUp(userSignUpDtoB));

    }

    UserSignUpDto getUserSignUpDto() {
        return UserSignUpDto.builder()
                .username("username")
                .password("password")
                .build();
    }
}