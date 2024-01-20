package com.account.yomankum.accountBook.domain.record;
import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.dto.request.RecordUpdateRequest;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import java.time.LocalDateTime;
import java.util.List;
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
        doNothing().when(accountBook).checkAuthorizedUser(ownerId);
        doThrow(new BadRequestException(Exception.ACCOUNT_BOOK_NOT_FOUND)).when(accountBook)
                .checkAuthorizedUser(otherUserId);
        doThrow(new BadRequestException(Exception.ACCOUNT_BOOK_NOT_FOUND)).when(accountBook)
                .deleteRecord(record, otherUserId);

        record = createRecord();
        recordUpdateRequest = createUpdateRequest();
    }

    private Record createRecord() {
        return Record.builder()
                .id(1L)
                .accountBook(accountBook)
                .content("초기 내역")
                .majorTag(DefaultCategory.FOOD.getTitle())
                .minorTag(List.of("마라탕", "배민"))
                .recordType(RecordType.INCOME)
                .money(10000)
                .build();
    }

    private RecordUpdateRequest createUpdateRequest() {
        return new RecordUpdateRequest("업데이트 내역",
                DefaultCategory.BONUS.getTitle(),
                List.of("명절"),
                RecordType.EXPENDITURE,
                50000,
                LocalDateTime.now());
    }

    @Test
    @DisplayName("권한이 있는 사용자가 가계부 내역을 수정한다.")
    void update_success() {
        Record record = createRecord();
        Record sameRecord = createRecord();

        record.update(recordUpdateRequest, ownerId);

        assertNotEquals(sameRecord.getContent(), record.getContent());
        assertNotEquals(sameRecord.getMajorTag(), record.getMajorTag());
        assertNotEquals(sameRecord.getMoney(), record.getMoney());
    }

    @Test
    @DisplayName("권한이 있는 사용자가 가계부 내역을 수정하면 에러가 발생한다.")
    void update_fail_unauthorizedUser() {
        assertThrows(BadRequestException.class, () ->
                record.update(recordUpdateRequest, otherUserId));
    }

    @Test
    @DisplayName("권한이 있는 사용자가 가계부를 삭제한다.")
    void delete_success() {
        record.delete(ownerId);
        verify(accountBook, times(1)).deleteRecord(record, ownerId);
    }

}