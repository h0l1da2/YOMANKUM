package com.account.yomankum.auth.jwt.service;

import com.account.yomankum.common.IntegrationTest;
import com.account.yomankum.user.domain.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TokenServiceIntegrationTest extends IntegrationTest {

    @Autowired TokenService tokenService;

    private Long userId = 1L;
    private String nickname = "testUser";

    @Test
    public void makeAndResolveTest(){
        String token = tokenService.creatToken(userId, nickname, UserType.USER);
        assertEquals(tokenService.getUserId(token), userId);
    }

    @Test
    public void validTokenTest(){
        String token = tokenService.creatToken(userId, nickname, UserType.USER);
        assertTrue(tokenService.tokenValid(token));
    }

}
