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
    private Long generalUserId;
    private Long readOnlyUserId;
    private Long notAuthUserId;
    private Tag tag;

    @BeforeEach
    void setUp() {
        ownerId = 1L;
        generalUserId = 2L;
        readOnlyUserId = 3L;
        notAuthUserId = 4L;

        List<Tag> tags = new ArrayList<>();
        tag = new Tag(1L,"main tag 1", accountBook, new Color());
        tags.add(tag);

        User user = User.builder().id(ownerId).build();
        User generalUser = User.builder().id(generalUserId).build();
        User readOnlyUser = User.builder().id(readOnlyUserId).build();

        AccountBookUser accountBookOwner = AccountBookUser.builder().id(1L).user(user).accountBookRole(AccountBookRole.OWNER).build();
        AccountBookUser generalAccountBookUser = AccountBookUser.builder().id(2L).user(generalUser).accountBookRole(AccountBookRole.GENERAL).build();
        AccountBookUser readOnlyAccountBookUser = AccountBookUser.builder().id(3L).user(readOnlyUser).accountBookRole(AccountBookRole.READ_ONLY).build();

        List<AccountBookUser> accountBookUsers = new ArrayList<>();
        accountBookUsers.add(accountBookOwner);
        accountBookUsers.add(generalAccountBookUser);
        accountBookUsers.add(readOnlyAccountBookUser);

        user.addAccountBook(accountBookOwner);
        generalUser.addAccountBook(generalAccountBookUser);
        readOnlyUser.addAccountBook(readOnlyAccountBookUser);
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
    @DisplayName("권한이 없는 사용자가 가계부 수정 시 에러가 발생하며 가계부 이름은 그대로 유지된다. (general user)")
    void updateName_fail_general() {
        assertThrows(BadRequestException.class, ()
                -> accountBook.updateName("Updated Name", generalUserId));
        assertEquals(ACCOUNT_BOOK_NAME, accountBook.getName());
    }

    @Test
    @DisplayName("권한이 없는 사용자가 가계부 수정 시 에러가 발생하며 가계부 이름은 그대로 유지된다. (readonly user)")
    void updateName_fail_readonly() {
        assertThrows(BadRequestException.class, ()
                -> accountBook.updateName("Updated Name", readOnlyUserId));
        assertEquals(ACCOUNT_BOOK_NAME, accountBook.getName());
    }

    @Test
    @DisplayName("권한이 있는 사용자가 가계부에 내역을 추가한다. (OWNER)")
    void addRecord_success_owner() {
        Record newRecord = createNewRecord();
        accountBook.addRecord(newRecord, ownerId);
        assertTrue(accountBook.getRecords().contains(newRecord));
        assertEquals(accountBook, newRecord.getAccountBook());
    }

    @Test
    @DisplayName("권한이 있는 사용자가 가계부에 내역을 추가한다. (OWNER)")
    void addRecord_success_general() {
        Record newRecord = createNewRecord();
        accountBook.addRecord(newRecord, generalUserId);
        assertTrue(accountBook.getRecords().contains(newRecord));
        assertEquals(accountBook, newRecord.getAccountBook());
    }

    @Test
    @DisplayName("가계부에 포함되어 있지만 GENERAL 권한이 아닌 사용자가 가계부에 내역을 추가 시도하면 에러가 발생한다.")
    void addRecord_fail_not_general() {
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
    @DisplayName("가계부에 유저를 추가한다")
    public void add_accountBookUser() {
        AccountBook accountBook = new AccountBook();
        User user = makeTestUser();
        accountBook.addAccountBookUser(user, AccountBookRole.OWNER, AccountBookUserStatus.PARTICIPATING);

        assertEquals(1, accountBook.getAccountBookUsers().size());
        assertTrue(accountBook.getAccountBookUsers().stream()
                .anyMatch(accountBookUser -> accountBookUser.getUser().getId().equals(1L)));
    }

    @Test
    @DisplayName("가계부에서 유저를 제거한다")
    public void remove_User() {
        AccountBook accountBook = new AccountBook();
        User user = makeTestUser();
        accountBook.addAccountBookUser(user, AccountBookRole.OWNER, AccountBookUserStatus.PARTICIPATING);

        accountBook.removeUser(1L, 1L);

        assertFalse(accountBook.getAccountBookUsers().stream()
                .anyMatch(accountBookUser -> accountBookUser.getUser().getId().equals(1L)));
    }

    private User makeTestUser() {
        return User.builder()
                .id(1L)
                .email("testUser@test.com")
                .build();
    }

    private Record createNewRecord() {
        return Record.builder()
                .content("test record")
                .build();
    }

}
