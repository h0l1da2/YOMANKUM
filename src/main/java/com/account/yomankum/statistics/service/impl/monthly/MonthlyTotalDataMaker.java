package com.account.yomankum.statistics.service.impl.monthly;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.statistics.service.impl.monthly.vo.MonthlyTotal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthlyTotalDataMaker {

    private MonthlyTotalDataMaker(){}

    public static List<MonthlyTotal> createMonthlyTotalData(List<Record> records, YearMonth from, YearMonth to){
        Map<YearMonth, MonthlyTotal> monthlyTotals = makeDefaultMonthlyTotals(from, to);
        for(Record record : records){
            LocalDate recordedDate = record.getDate();
            YearMonth yearMonth = YearMonth.of(recordedDate.getYear(), recordedDate.getMonth());
            MonthlyTotal monthlyTotal = monthlyTotals.getOrDefault(yearMonth, new MonthlyTotal(yearMonth, 0L, 0L));
            monthlyTotal.accumulate(record);
            monthlyTotals.put(yearMonth, monthlyTotal);
        }
        return monthlyTotals.values().stream().sorted().toList();
    }

    // 수입(혹은 지출)이 없는 달의 데이터도 포함하기 위함
    private static Map<YearMonth, MonthlyTotal> makeDefaultMonthlyTotals(YearMonth start, YearMonth end) {
        Map<YearMonth, MonthlyTotal> defaultMonthlyTotals = new HashMap<>();
        while(!start.isAfter(end)){
            YearMonth yearMonth = YearMonth.of(start.getYear(), start.getMonth());
            defaultMonthlyTotals.put(yearMonth, new MonthlyTotal(yearMonth, 0L, 0L));
            start = start.plusMonths(1);
        }
        return defaultMonthlyTotals;
    }

}
