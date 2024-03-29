package com.account.yomankum.accountBook.service;

import static org.junit.jupiter.api.Assertions.*;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.AccountBookRepository;
import com.account.yomankum.accountBook.domain.AccountBookType;
import com.account.yomankum.accountBook.dto.request.AccountBookCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@WithMockUser(username = "1")
public class AccountBookServiceIntegrationTest {

    @Autowired
    private AccountBookService accountBookService;

    @Autowired
    private AccountBookRepository accountBookRepository;

    @Test
    public void testCreateAccountBook() {
        AccountBookCreateRequest createRequest = new AccountBookCreateRequest("Test AccountBook", AccountBookType.PRIVATE);
        Long accountBookId = accountBookService.create(createRequest);

        AccountBook foundAccountBook = accountBookRepository.findById(accountBookId).orElse(null);
        assertNotNull(foundAccountBook);
        assertEquals("Test AccountBook", foundAccountBook.getName());
    }

    @Test
    public void testUpdateAccountBook() {
        AccountBookCreateRequest createRequest = new AccountBookCreateRequest("Test AccountBook", AccountBookType.PRIVATE);
        Long accountBookId = accountBookService.create(createRequest);

        accountBookService.update(accountBookId, "Updated Name");

        AccountBook updatedAccountBook = accountBookRepository.findById(accountBookId).orElse(null);
        assertNotNull(updatedAccountBook);
        assertEquals("Updated Name", updatedAccountBook.getName());
    }

    @Test
    public void testDeleteAccountBook() {
        AccountBookCreateRequest createRequest = new AccountBookCreateRequest("Test AccountBook", AccountBookType.PRIVATE);
        Long accountBookId = accountBookService.create(createRequest);

        accountBookService.delete(accountBookId);

        AccountBook foundAccountBook = accountBookRepository.findById(accountBookId).orElse(null);
        assertNull(foundAccountBook);
    }
}



