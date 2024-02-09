package com.account.yomankum.statistics.vo;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordType;
import java.time.YearMonth;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MonthlyTotal {

    private final YearMonth yearMonth;
    private Long totalIncome;
    private Long totalExpenditure;

    protected void accumulate(Record record) {
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
}
