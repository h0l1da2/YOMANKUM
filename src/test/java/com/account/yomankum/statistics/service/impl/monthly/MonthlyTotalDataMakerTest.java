package com.account.yomankum.statistics.service.impl.monthly;
import static org.junit.jupiter.api.Assertions.*;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordType;
import com.account.yomankum.statistics.service.impl.monthly.vo.MonthlyTotal;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

public class MonthlyTotalDataMakerTest {

    @Test
    @DisplayName("일년 동안의 월별 지출입 총계 테스트")
    public void test1() {
        Record record1 = makeRecord(RecordType.EXPENDITURE, 10000, LocalDate.of(2024, 1, 1));
        Record record2 = makeRecord(RecordType.INCOME, 8000, LocalDate.of(2024, 1, 1));
        Record record3 = makeRecord(RecordType.EXPENDITURE, 20000, LocalDate.of(2024, 1, 2));
        Record record4 = makeRecord(RecordType.EXPENDITURE, 30000, LocalDate.of(2024, 2, 1));
        List<Record> records = Arrays.asList(record1, record2, record3, record4);
        YearMonth from = YearMonth.of(2024, 1);
        YearMonth to = YearMonth.of(2024, 12);

        List<MonthlyTotal> result = MonthlyTotalDataMaker.createMonthlyTotalData(records, from, to);

        assertEquals(12, result.size());
        assertEquals(result.get(0).getTotalExpenditure(), 30000);
        assertEquals(result.get(0).getTotalIncome(), 8000);
        assertEquals(result.get(1).getTotalExpenditure(), 30000);
        assertEquals(result.get(1).getTotalIncome(), 0);
        assertEquals(result.get(2).getTotalExpenditure(), 0);
        assertEquals(result.get(2).getTotalIncome(), 0);
    }

    @Test
    @DisplayName("조건 기간 내 record 가 없어도, 조건기간의 모든 월에 대한 데이터가 생성된다.")
    public void emptyRecord() {
        List<Record> records = Arrays.asList();
        YearMonth from = YearMonth.of(2024, 1);
        YearMonth to = YearMonth.of(2024, 12);

        List<MonthlyTotal> result = MonthlyTotalDataMaker.createMonthlyTotalData(records, from, to);

        assertEquals(12, result.size());
        assertTrue(result.stream().allMatch(monthlyTotal -> monthlyTotal.getTotalIncome() == 0 && monthlyTotal.getTotalExpenditure() == 0));
    }


    @Test
    @DisplayName("조회 기간 조건이 역전된 경우 빈 리스트 반환")
    public void illegalDateCondition() {
        List<Record> records = Arrays.asList();
        YearMonth from = YearMonth.of(2024, 3);
        YearMonth to = YearMonth.of(2024, 1);

        List<MonthlyTotal> monthlyTotals = MonthlyTotalDataMaker.createMonthlyTotalData(records, from, to);

        assertEquals(monthlyTotals.size(), 0);
    }

    private Record makeRecord(RecordType type, long money, LocalDate date){
        return Record.builder()
                .recordType(type)
                .money(money)
                .date(date)
                .build();
    }

}
