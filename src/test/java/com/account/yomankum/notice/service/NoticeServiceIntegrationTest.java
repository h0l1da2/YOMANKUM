package com.account.yomankum.notice.service;

import com.account.yomankum.notice.domain.Notice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@WithMockUser(username = "1")
public class NoticeServiceIntegrationTest {

    @Autowired private NoticeService noticeService;

    @Test
    public void testCreateNotice() {
        Notice notice = noticeService.save(1L, "content");

        assertNotNull(notice);
        assertEquals(notice.getContent(), "content");
    }

}



