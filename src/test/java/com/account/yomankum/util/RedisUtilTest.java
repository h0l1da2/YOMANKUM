package com.account.yomankum.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    @DisplayName("레디스 데이터 가져오기 성공")
    void 레디스_데이터_테스트() {

        String email = "infp0000@naver.com";
        String code = "asdf11";

        redisUtil.setDataExpire(email, code, 60 * 15);

        assertTrue(redisUtil.existData(email));
        assertFalse(redisUtil.existData(email+"1"));
        assertEquals(redisUtil.getData(email), code);
    }

}