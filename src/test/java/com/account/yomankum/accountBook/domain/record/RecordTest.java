package com.account.yomankum.accountBook.domain.record;
import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.tag.DefaultTag;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.RecordUpdateRequest;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecordTest {

    private Record record;
    private AccountBook accountBook;
    private Long ownerId;
    private Long otherUserId;
    private RecordUpdateRequest recordUpdateRequest;

    @BeforeEach
    void setUp() {
        ownerId = 1L;
        otherUserId = 2L;

        accountBook = mock(AccountBook.class);
        doThrow(new BadRequestException(Exception.ACCOUNT_BOOK_NOT_FOUND)).when(accountBook)
                .checkHasGeneralAuth(otherUserId);
        doThrow(new BadRequestException(Exception.ACCOUNT_BOOK_NOT_FOUND)).when(accountBook)
                .deleteRecord(record, otherUserId);

        record = record();
        recordUpdateRequest = updateRequest();
    }

    @Test
    @DisplayName("권한이 있는 사용자가 가계부 내역을 수정한다.")
    void update_success() {
        Record record = record();
        Record sameRecord = record();
        Tag newTag = makeTag(2L, DefaultTag.BONUS.getName());

        record.update(recordUpdateRequest, newTag, ownerId);

        assertNotEquals(sameRecord.getContent(), record.getContent());
        assertNotEquals(sameRecord.getMainTag(), record.getMainTag());
        assertNotEquals(sameRecord.getAmount(), record.getAmount());
    }

    @Test
    @DisplayName("권한이 없는 사용자가 가계부 내역을 수정하면 에러가 발생한다.")
    void update_fail_unauthorizedUser() {
        Tag newTag = makeTag(2L, DefaultTag.BONUS.getName());

        assertThrows(BadRequestException.class, () ->
                record.update(recordUpdateRequest, newTag, otherUserId));
    }

    @Test
    @DisplayName("권한이 있는 사용자가 가계부를 삭제한다.")
    void delete_success() {
        record.delete(ownerId);
        verify(accountBook, times(1)).deleteRecord(record, ownerId);
    }

    private Record record() {
        return Record.builder()
                .id(1L)
                .accountBook(accountBook)
                .content("초기 내역")
                .mainTag(makeTag(1L, DefaultTag.FOOD.getName()))
                .subTags(Set.of("마라탕", "배민"))
                .recordType(RecordType.EXPENDITURE)
                .amount(10000)
                .build();
    }

    private Tag makeTag(Long id, String name){
        return Tag.builder()
                .id(id)
                .name(name)
                .build();
    }

    private RecordUpdateRequest updateRequest() {
        return new RecordUpdateRequest("업데이트 내역",
                2L,
                Set.of("명절"),
                RecordType.INCOME,
                50000,
                LocalDate.now());
    }

}