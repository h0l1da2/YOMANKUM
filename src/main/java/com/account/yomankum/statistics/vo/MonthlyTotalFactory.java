package com.account.yomankum.statistics.vo;

import com.account.yomankum.accountBook.domain.record.Record;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthlyTotalFactory {

    private MonthlyTotalFactory(){}

    public static List<MonthlyTotal> createMonthlyTotalData(List<Record> records){
        Map<YearMonth, MonthlyTotal> monthlyTotals = new HashMap<>();
        for(Record record : records){
            LocalDate recordedDate = record.getDate();
            YearMonth yearMonth = YearMonth.of(recordedDate.getYear(), recordedDate.getMonth());
            MonthlyTotal monthlyTotal = monthlyTotals.getOrDefault(yearMonth, new MonthlyTotal(yearMonth));
            monthlyTotal.accumulate(record);
            monthlyTotals.put(yearMonth, monthlyTotal);
        }
        return monthlyTotals.values().stream().toList();
    }

}
