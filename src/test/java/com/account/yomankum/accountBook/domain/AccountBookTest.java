package com.account.yomankum.accountBook.domain;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.tag.Color;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountBookTest {

    private static final String ACCOUNT_BOOK_NAME = "Test AccountBook";

    private AccountBook accountBook;
    private Long ownerId;
    private Long otherUserId;
    private Long readOnlyUserId;
    private Long notAuthUserId;
    private Tag tag;

    @BeforeEach
    void setUp() {
        ownerId = 1L;
        otherUserId = 2L;
        notAuthUserId = 3L;
        readOnlyUserId = 4L;

        List<Tag> tags = new ArrayList<>();
        tag = new Tag(1L,"main tag 1", accountBook, new Color());
        tags.add(tag);

        User user = User.builder().id(ownerId).build();
        User otherUser = User.builder().id(otherUserId).build();
        User readOnlyUser = User.builder().id(readOnlyUserId).build();

        AccountBookUser accountBookUser = AccountBookUser.builder().id(1L).user(user).accountBookRole(AccountBookRole.OWNER).build();
        AccountBookUser accountBookOtherUser = AccountBookUser.builder().id(2L).user(otherUser).accountBookRole(AccountBookRole.GENERAL).build();
        AccountBookUser accountBookReadOnlyUser = AccountBookUser.builder().id(3L).user(readOnlyUser).accountBookRole(AccountBookRole.READ_ONLY).build();

        List<AccountBookUser> accountBookUsers = new ArrayList<>();
        accountBookUsers.add(accountBookUser);
        accountBookUsers.add(accountBookOtherUser);
        accountBookUsers.add(accountBookReadOnlyUser);

        user.addAccountBook(accountBookUser);
        otherUser.addAccountBook(accountBookOtherUser);
        accountBook = AccountBook.builder()
                .id(1L)
                .name(ACCOUNT_BOOK_NAME)
                .mainTags(tags)
                .accountBookUsers(accountBookUsers)
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
    @DisplayName("가계부에 포함되어 있지만 OWNER 권한이 아닌 사용자가 가계부에 내역을 추가 시도하면 에러가 발생한다.")
    void addRecord_fail_not_owner() {
        Record newRecord = createNewRecord();
        assertThrows(BadRequestException.class, ()
                -> accountBook.addRecord(newRecord, readOnlyUserId));
        assertFalse(accountBook.getRecords().contains(newRecord));
    }

    @Test
    @DisplayName("가계부에 권한이 없는 사용자가 가계부에 내역을 추가 시도하면 에러가 발생한다.")
    void addRecord_fail() {
        Record newRecord = createNewRecord();
        assertThrows(BadRequestException.class, ()
                -> accountBook.addRecord(newRecord, notAuthUserId));
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
                accountBook.checkAuthorizedUser(notAuthUserId));
        assertEquals(Exception.ACCOUNT_BOOK_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("대분류 태그를 추가한다")
    void add_mainTag(){
        int originMainTagSize = accountBook.getMainTags().size();
        Tag tag = Tag.builder().id(2L).name("new tag").build();
        accountBook.addTag(tag, ownerId);

        assertNotEquals(originMainTagSize, accountBook.getMainTags().size());
        assertTrue(accountBook.getMainTags().contains(tag));
    }

    @Test
    @DisplayName("대분류 태그를 추가한다")
    void remove_mainTag(){
        int originMainTagSize = accountBook.getMainTags().size();
        accountBook.deleteTag(tag, ownerId);

        assertNotEquals(originMainTagSize, accountBook.getMainTags().size());
        assertTrue(accountBook.getMainTags().isEmpty());
    }

    @Test
    @DisplayName("오너가 아닌 가계부 유저가 가계부를 삭제한다.")
    void remove_accountBook(){
        assertThrows(BadRequestException.class, () ->accountBook.delete(otherUserId));
    }

    private Record createNewRecord() {
        return Record.builder()
                .content("test record")
                .build();
    }

}
