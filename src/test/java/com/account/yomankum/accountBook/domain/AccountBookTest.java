package com.account.yomankum.accountBook.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class AccountBookTest {

    private static final String ACCOUNT_BOOK_NAME = "Test AccountBook";

    private AccountBook accountBook;
    private Long ownerId;
    private Long otherUserId;

    @BeforeEach
    void setUp() {
        ownerId = 1L;
        otherUserId = 2L;
        accountBook = AccountBook.builder()
                .id(1L)
                .name(ACCOUNT_BOOK_NAME)
                .build();
        setUserAsCreator(accountBook, ownerId);
    }

    private void setUserAsCreator(AccountBook accountBook, Long userId) {
        ReflectionTestUtils.setField(accountBook, "createUser", userId.toString());
    }

    @Test
    @DisplayName("권한이 있는 사용자가 가계부 이름을 수정한다.")
    void updateName_success() {
        accountBook.updateName("Updated Name", ownerId);
        assertEquals("Updated Name", accountBook.getName());
    }

    @Test
    @DisplayName("권한이 없는 사용자가 가계부 수정 시 에러가 발생하며 가계부 이름은 그대로 유지된다.")
    void updateName_fail() {
        assertThrows(BadRequestException.class, ()
                -> accountBook.updateName("Updated Name", otherUserId));
        assertEquals(ACCOUNT_BOOK_NAME, accountBook.getName());
    }

    @Test
    @DisplayName("권한이 있는 사용자가 가계부에 내역을 추가한다.")
    void addRecord_success() {
        Record newRecord = createNewRecord();
        accountBook.addRecord(newRecord, ownerId);
        assertTrue(accountBook.getRecords().contains(newRecord));
        assertEquals(accountBook, newRecord.getAccountBook());
    }

    @Test
    @DisplayName("권한이 없는 사용자가 가계부에 내역을 추가 시도하면 에러가 발생한다.")
    void addRecord_fail() {
        Record newRecord = createNewRecord();
        assertThrows(BadRequestException.class, ()
                -> accountBook.addRecord(newRecord, otherUserId));
        assertFalse(accountBook.getRecords().contains(newRecord));
    }

    @Test
    @DisplayName("권한이 있는 사용자가 가계부의 내역을 삭제한다.")
    void deleteRecord_success() {
        Record record = createNewRecord();
        accountBook.addRecord(record, ownerId);
        accountBook.deleteRecord(record, ownerId);
        assertFalse(accountBook.getRecords().contains(record));
    }

    @Test
    @DisplayName("가계부에 대한 사용자의 권한을 검사한다. (권한이 있는 유저의 경우)")
    void checkAuthorizedUser_success() {
        assertDoesNotThrow(() -> accountBook.checkAuthorizedUser(ownerId));
    }

    @Test
    @DisplayName("가계부에 대한 사용자의 권한을 검사한다. (권한이 없는 유저의 경우)")
    void checkAuthorizedUser_fail() {
        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                accountBook.checkAuthorizedUser(otherUserId));
        assertEquals(Exception.ACCOUNT_BOOK_NOT_FOUND.getMessage(), exception.getMessage());
    }

    private Record createNewRecord() {
        return Record.builder()
                .content("test record")
                .build();
    }

}
