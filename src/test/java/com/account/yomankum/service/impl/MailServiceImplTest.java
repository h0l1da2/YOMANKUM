package com.account.yomankum.service.impl;

import com.account.yomankum.domain.Mail;
import com.account.yomankum.service.MailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailServiceImplTest {

    @Autowired
    private MailService mailService;

    @Test
    @DisplayName("회원가입 메일 보내기 성공")
    void 메일보내기_성공() throws MessagingException {
        String code = mailService.mailSend(Mail.JOIN, "holiday.k1@icloud.com");

        assertNotNull(code);
        System.out.println("code = " + code);
    }
}