package com.account.yomankum.common;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@WithMockUser("1")
public abstract class IntegrationTest {
}
