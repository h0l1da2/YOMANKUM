package com.account.yomankum.accountBook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.AccountBookRepository;
import com.account.yomankum.accountBook.domain.AccountBookType;
import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import com.account.yomankum.accountBook.domain.record.RecordType;
import com.account.yomankum.accountBook.dto.request.RecordCreateRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@WithMockUser(username = "1")
public class RecordFinderIntegrationTest {

    @Autowired
    private RecordFinder recordFinder;
    @Autowired
    private RecordService recordService;
    @Autowired
    private AccountBookRepository accountBookRepository;

    private AccountBook accountBook;
    private LocalDateTime today;
    private LocalDateTime yesterday;
    private LocalDateTime twoDaysAgo;

    @BeforeEach
    public void setup() {
        accountBook = createAccountBook();
        accountBookRepository.save(accountBook);
        today = LocalDateTime.now();
        yesterday = LocalDateTime.now().minusDays(1);
        twoDaysAgo = LocalDateTime.now().minusDays(2);
        recordService.addRecord(accountBook.getId(),
                makeRecordRequest("지출 내역1", today, "대분류1", RecordType.EXPENDITURE, 10000, "소분류1", "소분류2"));
        recordService.addRecord(accountBook.getId(),
                makeRecordRequest("지출 내역2", yesterday, "대분류2", RecordType.EXPENDITURE, 20000, "소분류2", "소분류3"));
        recordService.addRecord(accountBook.getId(),
                makeRecordRequest("수입 내역1", twoDaysAgo, "대분류3", RecordType.INCOME, 30000, "소분류3","소분류4"));
    }

    private RecordCreateRequest makeRecordRequest(
            String content,
            LocalDateTime date,
            String majorTag,
            RecordType recordType,
            int money,
            String... minorTag) {
        return new RecordCreateRequest(content, majorTag, Arrays.stream(minorTag).toList(), recordType, money, date);
    }

    private AccountBook createAccountBook() {
        return AccountBook.builder()
                .type(AccountBookType.PRIVATE)
                .name("test account book")
                .build();
    }

    @Test
    public void searchByContent() {
        RecordSearchCondition recordSearchCondition = RecordSearchCondition.builder()
                .content("지출")
                .pageSize(100)
                .build();
        List<Record> records = recordFinder.searchRecords(accountBook.getId(), recordSearchCondition);

        assertEquals(2, records.size());
        assertTrue(records.get(0).getContent().contains("지출"));
        assertTrue(records.get(1).getContent().contains("지출"));
    }

    @Test
    public void searchByContentAndRecordType() {
        RecordSearchCondition recordSearchCondition = RecordSearchCondition.builder()
                .content("지출")
                .recordType(RecordType.EXPENDITURE)
                .pageSize(100)
                .build();
        List<Record> records = recordFinder.searchRecords(accountBook.getId(), recordSearchCondition);

        assertEquals(2, records.size());
        assertTrue(records.get(0).getContent().contains("지출") && records.get(0).getRecordType() == RecordType.EXPENDITURE);
        assertTrue(records.get(1).getContent().contains("지출") && records.get(1).getRecordType() == RecordType.EXPENDITURE);
    }

    @Test
    public void searchByRecordTypeAndMoney(){
        RecordSearchCondition recordSearchCondition = RecordSearchCondition.builder()
                .recordType(RecordType.EXPENDITURE)
                .minMoney(15000)
                .pageSize(100)
                .build();
        List<Record> records = recordFinder.searchRecords(accountBook.getId(), recordSearchCondition);

        assertEquals(1, records.size());
        assertTrue(15000 <= records.get(0).getMoney() &&records.get(0).getRecordType() == RecordType.EXPENDITURE);
    }

    @Test
    public void searchByMoney(){
        RecordSearchCondition recordSearchCondition = RecordSearchCondition.builder()
                .minMoney(15000)
                .pageSize(100)
                .build();
        List<Record> records = recordFinder.searchRecords(accountBook.getId(), recordSearchCondition);

        assertEquals(2, records.size());
        assertTrue(15000 <= records.get(0).getMoney());
        assertTrue(15000 <= records.get(1).getMoney());
    }

    @Test
    public void searchByMinorTag(){
        RecordSearchCondition recordSearchCondition = RecordSearchCondition.builder()
                .minorTag("소분류3")
                .pageSize(100)
                .build();
        List<Record> records = recordFinder.searchRecords(accountBook.getId(), recordSearchCondition);

        assertEquals(2, records.size());
    }

    @Test
    public void searchByDateFrom(){
        RecordSearchCondition recordSearchCondition = RecordSearchCondition.builder()
                .from(twoDaysAgo.plusMinutes(1))
                .pageSize(100)
                .build();
        List<Record> records = recordFinder.searchRecords(accountBook.getId(), recordSearchCondition);

        assertEquals(2, records.size());
        assertTrue(records.get(0).getDate().isAfter(twoDaysAgo));
        assertTrue(records.get(1).getDate().isAfter(twoDaysAgo));
    }

    @Test
    public void searchByDateTo(){
        RecordSearchCondition recordSearchCondition = RecordSearchCondition.builder()
                .to(today.minusMinutes(1))
                .pageSize(100)
                .build();
        List<Record> records = recordFinder.searchRecords(accountBook.getId(), recordSearchCondition);

        assertEquals(2, records.size());
        assertTrue(records.get(0).getDate().isBefore(today));
        assertTrue(records.get(1).getDate().isBefore(today));
    }

    @Test
    public void searchByDateFromTo(){
        RecordSearchCondition recordSearchCondition = RecordSearchCondition.builder()
                .from(twoDaysAgo.plusMinutes(1))
                .to(today.minusMinutes(1))
                .pageSize(100)
                .build();
        List<Record> records = recordFinder.searchRecords(accountBook.getId(), recordSearchCondition);

        assertEquals(1, records.size());
        assertTrue(records.get(0).getDate().isAfter(twoDaysAgo) && records.get(0).getDate().isBefore(today));
    }


}