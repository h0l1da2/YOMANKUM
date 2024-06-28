package com.account.yomankum.common;

import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class IntegrationTest {

    @Autowired UserRepository userRepository;

    public User testUser;

    @BeforeEach
    public void setTestUser(){
        User user = User.builder()
                .nickname("testUser1")
                .email("testUser1@test.com")
                .build();
        userRepository.save(user);
        testUser = user;
    }
}
