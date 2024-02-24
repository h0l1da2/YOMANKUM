package com.account.yomankum.statistics.service.impl.monthly.vo;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordType;
import com.account.yomankum.statistics.dto.StatisticsResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.YearMonth;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlyTotal implements StatisticsResponse, Comparable<MonthlyTotal> {

    private final YearMonth yearMonth;
    private Long totalIncome;
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
