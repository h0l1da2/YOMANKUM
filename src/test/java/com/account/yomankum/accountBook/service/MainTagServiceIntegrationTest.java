package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.AccountBookRepository;
import com.account.yomankum.accountBook.domain.tag.Color;
import com.account.yomankum.accountBook.domain.tag.MainTagRepository;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.AccountBookCreateRequest;
import com.account.yomankum.accountBook.dto.request.MainTagRequest;
import com.account.yomankum.common.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class MainTagServiceIntegrationTest extends IntegrationTest {

    @Autowired private MainTagService mainTagService;
    @Autowired private MainTagRepository mainTagRepository;
    @Autowired private AccountBookRepository accountBookRepository;
    @Autowired private AccountBookService accountBookService;

    private AccountBook accountBook;
    private Tag tag;

    @BeforeEach
    void setup(){
        AccountBookCreateRequest request = new AccountBookCreateRequest("test account book");
        Long accountBookId = accountBookService.create(request, testUser.getId());
        accountBook = accountBookRepository.findById(accountBookId).get();

        tag = new Tag(null, "main tag1", accountBook, new Color());
        mainTagRepository.save(tag);
        accountBook.getMainTags().add(tag);
    }

    @Test
    @DisplayName("대분류 태그 생성.")
    public void create_mainTag(){
        int previousTagSize = accountBook.getMainTags().size();
        MainTagRequest createRequest = new MainTagRequest("new tag");
        Tag createdTag = mainTagService.create(accountBook.getId(), createRequest, testUser.getId());
        int currentTagSize = accountBook.getMainTags().size();

        assertEquals(createdTag, mainTagRepository.findById(createdTag.getId()).orElse(null));
        assertNotEquals(previousTagSize, currentTagSize);
        assertTrue(accountBook.getMainTags().contains(createdTag));
    }

    @Test
    @DisplayName("대분류 태그 수정")
    public void update_mainTag(){
        String newName = "new name";
        mainTagService.update(tag.getId(), new MainTagRequest("new name"), testUser.getId());

        assertEquals(newName, mainTagRepository.findById(tag.getId()).orElse(new Tag()).getName());
    }

    @Test
    @DisplayName("대분류 태그 삭제.")
    public void delete_mainTag(){
        int previousTagSize = accountBook.getMainTags().size();
        mainTagService.delete(tag.getId(), testUser.getId());
        int currentTagSize = accountBook.getMainTags().size();

        assertNotEquals(previousTagSize, currentTagSize);
        assertFalse(accountBook.getMainTags().contains(tag));
        assertTrue(mainTagRepository.findById(tag.getId()).isEmpty());
    }

}