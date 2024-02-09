package com.account.yomankum.statistics.service;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import com.account.yomankum.accountBook.domain.record.RecordType;
import com.account.yomankum.accountBook.service.RecordFinder;
import com.account.yomankum.statistics.vo.MonthlyTotal;
import com.account.yomankum.statistics.vo.MonthlyTotalFactory;
import com.account.yomankum.statistics.vo.TagRate;
import com.account.yomankum.statistics.vo.TagRateFactory;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final RecordFinder recordFinder;

    public List<MonthlyTotal> getMonthlyTotalData(Long accountBookId, YearMonth from, YearMonth to) {
        RecordSearchCondition condition = RecordSearchCondition.of(from.atDay(1), to.atEndOfMonth());
        List<Record> records = recordFinder.searchRecords(accountBookId, condition);
        return MonthlyTotalFactory.createMonthlyTotalData(records);
    }

    public List<TagRate> getMonthlyExpenditureMajorTagRate(Long accountBookId, YearMonth yearMonth) {
        RecordSearchCondition condition = RecordSearchCondition.of(yearMonth, RecordType.EXPENDITURE);
        List<Record> records = recordFinder.searchRecords(accountBookId, condition);
        return TagRateFactory.createMajorTagRateData(records);
    }

    public List<TagRate> getMonthlyIncomeMajorTagRate(Long accountBookId, YearMonth yearMonth) {
        RecordSearchCondition condition = RecordSearchCondition.of(yearMonth, RecordType.INCOME);
        List<Record> records = recordFinder.searchRecords(accountBookId, condition);
        return TagRateFactory.createMajorTagRateData(records);
    }
}
