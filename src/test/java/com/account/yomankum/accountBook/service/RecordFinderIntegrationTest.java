package com.account.yomankum.accountBook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.AccountBookRepository;
import com.account.yomankum.accountBook.domain.AccountBookType;
import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import com.account.yomankum.accountBook.domain.record.RecordType;
import com.account.yomankum.accountBook.domain.tag.DefaultTag;
import com.account.yomankum.accountBook.domain.tag.MainTagRepository;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.RecordCreateRequest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
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
    @Autowired
    private MainTagRepository mainTagRepository;

    private AccountBook accountBook;
    private LocalDate today;
    private LocalDate yesterday;
    private LocalDate twoDaysAgo;

    @BeforeEach
    public void setup() {
        accountBook = accountBook();
        accountBookRepository.save(accountBook);
        mainTagRepository.save(makeTag(1L, DefaultTag.FOOD.getName()));
        today = LocalDate.now();
        yesterday = LocalDate.now().minusDays(1);
        twoDaysAgo = LocalDate.now().minusDays(2);
        recordService.addRecord(accountBook.getId(),
                makeRecordRequest("지출 내역1", today, 1L, RecordType.EXPENDITURE, 10000, "소분류1", "소분류2"));
        recordService.addRecord(accountBook.getId(),
                makeRecordRequest("지출 내역2", yesterday, 1L, RecordType.EXPENDITURE, 20000, "소분류2", "소분류3"));
        recordService.addRecord(accountBook.getId(),
                makeRecordRequest("수입 내역1", twoDaysAgo, 1L, RecordType.INCOME, 30000, "소분류3","소분류4"));
    }

    private Tag makeTag(Long id, String name) {
        return Tag.builder().id(id).name(name).build();
    }

    private RecordCreateRequest makeRecordRequest(
            String content,
            LocalDate date,
            Long mainTagId,
            RecordType recordType,
            int money,
            String... subTags) {
        return new RecordCreateRequest(content, mainTagId, Arrays.stream(subTags).collect(Collectors.toSet()), recordType, money, date);
    }

    private AccountBook accountBook() {
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
        assertTrue(15000 <= records.get(0).getAmount() &&records.get(0).getRecordType() == RecordType.EXPENDITURE);
    }

    @Test
    public void searchByMoney(){
        RecordSearchCondition recordSearchCondition = RecordSearchCondition.builder()
                .minMoney(15000)
                .pageSize(100)
                .build();
        List<Record> records = recordFinder.searchRecords(accountBook.getId(), recordSearchCondition);

        assertEquals(2, records.size());
        assertTrue(15000 <= records.get(0).getAmount());
        assertTrue(15000 <= records.get(1).getAmount());
    }

    @Test
    public void searchBySubTag(){
        RecordSearchCondition recordSearchCondition = RecordSearchCondition.builder()
                .subTagName("소분류3")
                .pageSize(100)
                .build();
        List<Record> records = recordFinder.searchRecords(accountBook.getId(), recordSearchCondition);

        assertEquals(2, records.size());
    }

    @Test
    @DisplayName("기간검색 - 시작일 기준")
    public void searchByDateFrom(){
        RecordSearchCondition recordSearchCondition = RecordSearchCondition.builder()
                .from(twoDaysAgo.plusDays(1))
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
                .to(today.minusDays(1))
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
                .from(twoDaysAgo.plusDays(1))
                .to(today.minusDays(1))
                .pageSize(100)
                .build();
        List<Record> records = recordFinder.searchRecords(accountBook.getId(), recordSearchCondition);

        assertEquals(1, records.size());
        assertTrue(records.get(0).getDate().isAfter(twoDaysAgo) && records.get(0).getDate().isBefore(today));
    }


}