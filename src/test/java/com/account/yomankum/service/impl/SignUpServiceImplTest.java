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
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignUpServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SignUpService signUpService;

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
        assertThat(findUser).isNotNull();
        assertThat(findUser.getUsername()).isEqualTo(userSignUpDto.getUsername());
        assertThat(findUser.getPassword()).isEqualTo(userSignUpDto.getPassword());
    }

    @Test
    @DisplayName("실패 : 중복 회원")
    void 회원가입_실패_중복회원() throws Exception {

        GenericContainer<?> ubuntuContainer = new MySQLContainer<>("mysql:latest")
                .withExposedPorts(3306)
                .withEnv("MYSQL_ROOT_PASSWORD", "in8282")
                .withEnv("MYSQL_DATABASE", "yomankum")
                .withEnv("MYSQL_USER", "yomankum")
                .withEnv("MYSQL_PASSWORD", "in8282")
                .withDatabaseName("mysql_yomankum")
                .withUsername("yomankum")
                .withPassword("in8282");

        ubuntuContainer.start();


        UserSignUpDto userSignUpDtoA = getUserSignUpDto();
        UserSignUpDto userSignUpDtoB = getUserSignUpDto();

        signUpService.signUp(userSignUpDtoA);
        assertThrows(UserDuplicateException.class, () -> signUpService.signUp(userSignUpDtoB));

        ubuntuContainer.stop();

    }

    UserSignUpDto getUserSignUpDto() {
        return UserSignUpDto.builder()
                .username("username")
                .password("password")
                .build();
    }
}