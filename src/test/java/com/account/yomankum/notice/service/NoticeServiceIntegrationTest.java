package com.account.yomankum.notice.service;

import com.account.yomankum.notice.domain.Notice;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class NoticeServiceIntegrationTest {

    @Autowired private NoticeService noticeService;
    @Autowired private UserRepository userRepository;
    private Long userId;
    private User user;

    @BeforeEach
    void setup(){
        userId = 1L;
        user = userRepository.save(User.builder().id(userId).build());
    }

    @Test
    public void testCreateNotice() {
        Notice notice = noticeService.save(user.getId(), "content");

        assertNotNull(notice);
        assertEquals(notice.getContent(), "content");
    }

}



