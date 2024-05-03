package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.AccountBookRepository;
import com.account.yomankum.accountBook.domain.AccountBookRole;
import com.account.yomankum.accountBook.domain.tag.Color;
import com.account.yomankum.accountBook.domain.tag.MainTagRepository;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.MainTagRequest;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@WithMockUser(username = "1")
class MainTagServiceIntegrationTest {

    @Autowired private MainTagService mainTagService;
    @Autowired private MainTagRepository mainTagRepository;
    @Autowired private AccountBookRepository accountBookRepository;
    @Autowired private UserRepository userRepository;
    private AccountBook accountBook;
    private Tag tag;

    @BeforeEach
    void setup(){
        User user = userRepository.save(User.builder().id(1L).build());
        accountBook = AccountBook.builder()
                .name("new account book")
                .build();
        accountBookRepository.save(accountBook);
        accountBook.addNewUser(user, AccountBookRole.OWNER);
        tag = new Tag(null, "main tag1", accountBook, new Color());
        mainTagRepository.save(tag);
        accountBook.getMainTags().add(tag);
    }

    @Test
    @DisplayName("대분류 태그 생성.")
    public void create_mainTag(){
        int previousTagSize = accountBook.getMainTags().size();
        MainTagRequest createRequest = new MainTagRequest("new tag");
        Tag createdTag = mainTagService.create(accountBook.getId(), createRequest);
        int currentTagSize = accountBook.getMainTags().size();

        assertEquals(createdTag, mainTagRepository.findById(createdTag.getId()).orElse(null));
        assertNotEquals(previousTagSize, currentTagSize);
        assertTrue(accountBook.getMainTags().contains(createdTag));
    }

    @Test
    @DisplayName("대분류 태그 수정")
    public void update_mainTag(){
        String newName = "new name";
        mainTagService.update(tag.getId(), new MainTagRequest("new name"));

        assertEquals(newName, mainTagRepository.findById(tag.getId()).orElse(new Tag()).getName());
    }

    @Test
    @DisplayName("대분류 태그 삭제.")
    public void delete_mainTag(){
        int previousTagSize = accountBook.getMainTags().size();
        mainTagService.delete(tag.getId());
        int currentTagSize = accountBook.getMainTags().size();

        assertNotEquals(previousTagSize, currentTagSize);
        assertFalse(accountBook.getMainTags().contains(tag));
        assertTrue(mainTagRepository.findById(tag.getId()).isEmpty());
    }

}