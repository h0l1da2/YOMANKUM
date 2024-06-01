package com.account.yomankum.test;

import com.account.yomankum.common.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestDocsTestController.class)
class RestDocsTestControllerTest extends ControllerTest {

    @Test
    void RestDocsTest() throws Exception {
        mockMvc.perform(get("/restDocsTest")).andExpect(status().isOk());
    }

}