package com.account.yomankum.statistics.service.impl.tagRate.minor;
import static org.junit.jupiter.api.Assertions.*;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.statistics.service.impl.tagRate.vo.TagRate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class MinorTagRateDataMakerTest {

    @Test
    @DisplayName("빈 record 리스트에 대하여 빈 결과 데이터를 반환한다.")
    public void emptyRecordList() {
        List<Record> records = List.of();

        List<TagRate> result = MinorTagRateDataMaker.createMinorTagRateData(records);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("소분류 비율 통계 - 하나의 대분류 안에 다수의 소분류가 있는 경우")
    public void test1() {
        Record record1 = makeRecord(10000, "아침", "배민");
        Record record2 = makeRecord(5000, "교통");
        List<Record> records = Arrays.asList(record1, record2);

        List<TagRate> result = MinorTagRateDataMaker.createMinorTagRateData(records);

        assertEquals(3, result.size());
        assertEquals(result.get(0).getRate(),40);
        assertEquals(result.get(1).getRate(),40);
        assertEquals(result.get(2).getRate(),20);
    }


    @Test
    @DisplayName("소분류 비율 통계 - 하나의 소분류에 대한 기록만 있는 경우")
    public void test2() {
        Record record1 = makeRecord(5000, "아침");
        Record record2 = makeRecord(3000, "아침");
        List<Record> records = Arrays.asList(record1, record2);

        List<TagRate> result = MinorTagRateDataMaker.createMinorTagRateData(records);

        assertEquals(1, result.size());
        assertEquals(result.get(0).getRate(), 100);
    }

    private Record makeRecord(long money, String... minorTags){
        return Record.builder()
                .money(money)
                .minorTag(Arrays.stream(minorTags).toList())
                .build();
    }

}