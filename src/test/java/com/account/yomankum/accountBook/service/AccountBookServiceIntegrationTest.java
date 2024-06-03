package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.AccountBookRepository;
import com.account.yomankum.accountBook.domain.AccountBookType;
import com.account.yomankum.accountBook.domain.tag.DefaultTag;
import com.account.yomankum.accountBook.domain.tag.MainTagRepository;
import com.account.yomankum.accountBook.dto.request.AccountBookCreateRequest;
import com.account.yomankum.accountBook.dto.request.AccountBookInviteRequest;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.repository.UserRepository;
import com.account.yomankum.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class AccountBookServiceIntegrationTest extends IntegrationTest {

    @Autowired private AccountBookService accountBookService;
    @Autowired private AccountBookRepository accountBookRepository;
    @Autowired private MainTagRepository mainTagRepository;
    @Autowired private UserRepository userRepository;

    private static final Long USER_ID = 1L;

    @Test
    public void testCreateAccountBook() {
        AccountBookCreateRequest createRequest = new AccountBookCreateRequest("Test AccountBook", AccountBookType.PRIVATE);
        Long accountBookId = accountBookService.create(createRequest, USER_ID);

        AccountBook foundAccountBook = accountBookRepository.findById(accountBookId).orElse(null);
        assertNotNull(foundAccountBook);
        assertEquals("Test AccountBook", foundAccountBook.getName());
        assertEquals(DefaultTag.values().length, foundAccountBook.getMainTags().size());
        assertEquals(DefaultTag.values().length, mainTagRepository.findByAccountBookId(foundAccountBook.getId()).size());
    }

    @Test
    public void testUpdateAccountBook() {
        AccountBookCreateRequest createRequest = new AccountBookCreateRequest("Test AccountBook", AccountBookType.PRIVATE);
        Long accountBookId = accountBookService.create(createRequest, USER_ID);

        accountBookService.update(accountBookId, "Updated Name", USER_ID);

        AccountBook updatedAccountBook = accountBookRepository.findById(accountBookId).orElse(null);
        assertNotNull(updatedAccountBook);
        assertEquals("Updated Name", updatedAccountBook.getName());
    }

    @Test
    public void testDeleteAccountBook() {
        AccountBookCreateRequest createRequest = new AccountBookCreateRequest("Test AccountBook", AccountBookType.PRIVATE);
        Long accountBookId = accountBookService.create(createRequest, USER_ID);

        accountBookService.delete(accountBookId, USER_ID);

        AccountBook foundAccountBook = accountBookRepository.findById(accountBookId).orElse(null);
        assertNull(foundAccountBook);
    }

    @Test
    public void testInviteAccountBook() {
        AccountBookCreateRequest createRequest = new AccountBookCreateRequest("Test AccountBook", AccountBookType.PRIVATE);
        Long accountBookId = accountBookService.create(createRequest, USER_ID);
        String inviteEmail = "testInviteUser@test.com";
        userRepository.save(User.builder().email(inviteEmail).build());

        AccountBookInviteRequest inviteRequest = new AccountBookInviteRequest(inviteEmail);
        accountBookService.invite(accountBookId, inviteRequest, USER_ID);

        AccountBook accountBook = accountBookRepository.findById(accountBookId).orElse(null);
        assertNotNull(accountBook);
        boolean isInviteUser = accountBook.getAccountBookUsers().stream()
                .anyMatch(accountBookUser -> accountBookUser.getUser().getEmail().equals(inviteEmail));
        assertTrue(isInviteUser);
    }
}



