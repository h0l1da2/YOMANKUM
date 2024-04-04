package com.account.yomankum.accountBook.controller;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.tag.Color;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.MainTagRequest;
import com.account.yomankum.accountBook.service.MainTagFinder;
import com.account.yomankum.accountBook.service.MainTagService;
import com.account.yomankum.common.AbstractRestDocsTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(MainTagController.class)
class MainTagControllerTest extends AbstractRestDocsTests {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MainTagFinder mainTagFinder;

    @MockBean
    private MainTagService mainTagService;

    @Test
    public void get_mainTags() throws Exception {
        AccountBook accountBook = mock(AccountBook.class);
        List<Tag> mockTags = Arrays.asList(new Tag(1L, "식비", accountBook, new Color("#FFFFFF", "#000000")),
                new Tag(2L, "교통", accountBook, new Color("#000000", "#000000")));
        given(mainTagFinder.findMainTagsByAccountBook(1L)).willReturn(mockTags);
        given(accountBook.getId()).willReturn(1L);

        mockMvc.perform(get("/api/v1/mainTag/{accountBookId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mockTags.size()));
    }

    @Test
    public void register_mainTag() throws Exception {
        MainTagRequest mainTagRequest = new MainTagRequest("여행");

        mockMvc.perform(post("/api/v1/mainTag/{accountBookId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mainTagRequest)))
                .andExpect(status().isOk());

        verify(mainTagService).create(eq(1L), any(MainTagRequest.class));
    }

    @Test
    public void update_tag() throws Exception {
        MainTagRequest mainTagRequest = new MainTagRequest("운동");

        mockMvc.perform(put("/api/v1/mainTag/{tagId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mainTagRequest)))
                .andExpect(status().isOk());

        verify(mainTagService).update(eq(1L), any(MainTagRequest.class));
    }

    @Test
    public void delete_tag() throws Exception {
        mockMvc.perform(delete("/api/v1/mainTag/{tagId}", 1L))
                .andExpect(status().isOk());

        verify(mainTagService).delete(1L);
    }

}