package com.account.yomankum.accountBook.domain.tag;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import com.account.yomankum.accountBook.domain.AccountBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TagTest {

    private AccountBook accountBook;
    private Long ownerId = 1L;

    @BeforeEach
    void setUp(){
        accountBook = mock(AccountBook.class);
        doNothing().when(accountBook).checkAuthorizedUser(ownerId);
    }

    @Test
    @DisplayName("태그 명 변경.")
    void update_tagName(){
        String originName = "origin";
        String newName = "new tag";
        Tag tag = new Tag(1L, originName, accountBook, new Color());

        tag.update(newName, ownerId);
        assertNotEquals(originName, tag.getName());
        assertEquals(newName, tag.getName());
    }

}