package com.account.yomankum.statistics.service.impl.tagRate.mainTag;
import static org.junit.jupiter.api.Assertions.*;

import com.account.yomankum.accountBook.domain.record.DefaultCategory;
import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.statistics.service.impl.tagRate.vo.TagRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class MainTagRateDataMakerTest {

    @Test
    @DisplayName("빈 record 리스트에 대하여 빈 결과 데이터를 반환한다.")
    public void emptyRecordList() {
        List<Record> records = List.of();

        List<TagRate> result = MainTagRateDataMaker.createMainTagRateData(records);

        assertTrue(result.isEmpty());
    }

    private Tag food;
    private Tag travel;

    @BeforeEach
    void setup() {
        food = Tag.builder().id(1L).name(DefaultCategory.FOOD.name()).build();
        travel = Tag.builder().id(2L).name(DefaultCategory.TRAVEL.name()).build();

    }

    @Test
    @DisplayName("대분류 태그 비율 통계 - 전체 태그 중 식비 90%, 교통 10% 사용")
    public void test1() {
        Record record1 = makeRecord(food, 10000);
        Record record2 = makeRecord(travel, 2000);
        Record record3 = makeRecord(food, 8000);
        List<Record> records = Arrays.asList(record1, record2, record3);

        List<TagRate> result = MainTagRateDataMaker.createMainTagRateData(records);

        assertEquals(2, result.size());
        assertEquals(food.getName(), result.get(0).getTag());
        assertEquals(90, result.get(0).getRate());
        assertEquals(travel.getName(), result.get(1).getTag());
        assertEquals(10, result.get(1).getRate());
    }


    @Test
    @DisplayName("대분류 태그 비율 통계 - 전체 태그 중 식비 100% 사용")
    public void test2() {
        Record record1 = makeRecord(food, 5000);
        Record record2 = makeRecord(food, 5000);
        List<Record> records = Arrays.asList(record1, record2);

        List<TagRate> result = MainTagRateDataMaker.createMainTagRateData(records);

        assertEquals(1, result.size());
        assertEquals(food.getName(), result.get(0).getTag());
        assertEquals(100, result.get(0).getRate());
    }

    @Test
    @DisplayName("대분류 태그 비율 통계 - 전체 태그 중 교통 100%, 식비 0% 사용 (식비 내역은 있으나 금액이 0인 경우)")
    public void test3() {
        Record record1 = makeRecord(food, 0);
        Record record2 = makeRecord(travel, 2000);
        List<Record> records = Arrays.asList(record1, record2);

        List<TagRate> result = MainTagRateDataMaker.createMainTagRateData(records);

        assertEquals(2, result.size());
        assertEquals(travel.getName(), result.get(0).getTag());
        assertEquals(100, result.get(0).getRate());
    }

    private Record makeRecord(Tag mainTag, long amount){
        return Record.builder()
                .mainTag(mainTag)
                .amount(amount)
                .build();
    }
}
