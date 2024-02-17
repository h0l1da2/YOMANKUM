package com.account.yomankum.statistics.service.impl.monthly.vo;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.YearMonth;
import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class MonthlyTotal implements Comparable<MonthlyTotal> {

    @Schema(description = "년도-달", example = "2024-01")
    private final YearMonth yearMonth;
    @Schema(description = "총 수입", example = "10000000")
    private Long totalIncome;
    @Schema(description = "총 지출", example = "4430000")
    private Long totalExpenditure;

    public void accumulate(Record record) {
        if(record.getRecordType() == RecordType.EXPENDITURE){
            this.addExpenditure(record.getMoney());
        } else {
            this.addIncome(record.getMoney());
        }
    }

    private void addIncome(Long income){
        totalIncome += income;
    }

    private void addExpenditure(Long expenditure){
        totalExpenditure += expenditure;
    }

    @Override
    public int compareTo(MonthlyTotal other) {
        return this.yearMonth.compareTo((other.getYearMonth()));
    }
}
