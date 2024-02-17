package com.account.yomankum.statistics.service.impl.monthly;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import com.account.yomankum.accountBook.service.RecordFinder;
import com.account.yomankum.statistics.service.StatisticsRequest;
import com.account.yomankum.statistics.service.StatisticsService;
import com.account.yomankum.statistics.service.StatisticsType;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonthlyTotalStatisticsService implements StatisticsService {

    private final RecordFinder recordFinder;

    @Override
    public StatisticsType getSupportType() {
        return StatisticsType.MONTHLY_TOTAL;
    }

    @Override
    public Object get(StatisticsRequest request) {
        MonthlyTotalStatisticRequest param = (MonthlyTotalStatisticRequest) request;
        YearMonth from = param.from();
        YearMonth to = param.to();
        Long accountBookId = param.accountBookId();

        RecordSearchCondition condition = RecordSearchCondition.of(from.atDay(1), to.atEndOfMonth());
        List<Record> records = recordFinder.searchRecords(accountBookId, condition);
        return MonthlyTotalDataMaker.createMonthlyTotalData(records, from, to);
    }

}
