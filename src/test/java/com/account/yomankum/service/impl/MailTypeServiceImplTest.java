package com.account.yomankum.service.impl;

import com.account.yomankum.user.domain.type.MailType;
import com.account.yomankum.user.service.MailService;
import com.account.yomankum.util.RedisUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.account.yomankum.user.dto.MailDto.EmailRequestDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailTypeServiceImplTest {

    @Autowired
    private MailService mailService;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    @DisplayName("회원가입 메일 보내기 성공")
    void 가입메일보내기_성공() {
        String code = mailService.mailSend(getEmailRequestDto(MailType.JOIN));

        assertNotNull(code);
        System.out.println("code = " + code);
    }

    @Test
    @DisplayName("메일 코드 레디스 저장 완료")
    void 레디스_코드_저장_성공() {
        String mail = "holiday.k1@icloud.com";
        String code = mailService.mailSend(getEmailRequestDto(MailType.JOIN));

        assertNotNull(code);

        String redisCode = redisUtil.getData(mail);

        assertTrue(code.contains(redisCode));
    }

    private EmailRequestDto getEmailRequestDto(MailType mailType) {
        return EmailRequestDto.builder()
                .email("holiday.k1@icloud.com")
                .mailType(mailType)
                .build();
    }
}