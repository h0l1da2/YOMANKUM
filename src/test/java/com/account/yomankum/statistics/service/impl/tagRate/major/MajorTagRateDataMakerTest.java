package com.account.yomankum.statistics.service.impl.tagRate.major;
import static org.junit.jupiter.api.Assertions.*;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.statistics.service.impl.tagRate.vo.TagRate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class MajorTagRateDataMakerTest {

    @Test
    @DisplayName("빈 record 리스트에 대하여 빈 결과 데이터를 반환한다.")
    public void emptyRecordList() {
        List<Record> records = List.of();

        List<TagRate> result = MajorTagRateDataMaker.createMajorTagRateData(records);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("대분류 태그 비율 통계 - 전체 태그 중 식비 90%, 교통 10% 사용")
    public void test1() {
        Record record1 = makeRecord("식비", 10000);
        Record record2 = makeRecord("교통", 2000);
        Record record3 = makeRecord("식비", 8000);
        List<Record> records = Arrays.asList(record1, record2, record3);

        List<TagRate> result = MajorTagRateDataMaker.createMajorTagRateData(records);

        assertEquals(2, result.size());
        assertEquals("식비", result.get(0).getTag());
        assertEquals(90, result.get(0).getRate());
        assertEquals("교통", result.get(1).getTag());
        assertEquals(10, result.get(1).getRate());
    }


    @Test
    @DisplayName("대분류 태그 비율 통계 - 전체 태그 중 식비 100% 사용")
    public void test2() {
        Record record1 = makeRecord("식비", 5000);
        Record record2 = makeRecord("식비", 5000);
        List<Record> records = Arrays.asList(record1, record2);

        List<TagRate> result = MajorTagRateDataMaker.createMajorTagRateData(records);

        assertEquals(1, result.size());
        assertEquals("식비", result.get(0).getTag());
        assertEquals(100, result.get(0).getRate());
    }

    @Test
    @DisplayName("대분류 태그 비율 통계 - 전체 태그 중 교통 100%, 식비 0% 사용 (식비 내역은 있으나 금액이 0인 경우)")
    public void test3() {
        Record record1 = makeRecord("식비", 0);
        Record record2 = makeRecord("교통", 2000);
        List<Record> records = Arrays.asList(record1, record2);

        List<TagRate> result = MajorTagRateDataMaker.createMajorTagRateData(records);

        assertEquals(2, result.size());
        assertEquals("교통", result.get(0).getTag());
        assertEquals(100, result.get(0).getRate());
    }

    private Record makeRecord(String majorTag, long money){
        return Record.builder()
                .majorTag(majorTag)
                .money(money)
                .build();
    }
}
