package com.account.yomankum.statistics.service.impl.monthly;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import com.account.yomankum.accountBook.service.RecordFinder;
import com.account.yomankum.statistics.dto.StatisticsRequest;
import com.account.yomankum.statistics.dto.StatisticsResponse;
import com.account.yomankum.statistics.service.StatisticsHandler;
import com.account.yomankum.statistics.service.StatisticsType;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonthlyTotalStatisticsHandler implements StatisticsHandler {

    private final RecordFinder recordFinder;

    @Override
    public StatisticsType getSupportType() {
        return StatisticsType.MONTHLY_TOTAL;
    }

    @Override
    public List<StatisticsResponse> getData(StatisticsRequest request) {
        MonthlyTotalStatisticRequest param = (MonthlyTotalStatisticRequest) request;
        YearMonth from = param.from();
        YearMonth to = param.to();
        Long accountBookId = param.accountBookId();

        RecordSearchCondition condition = RecordSearchCondition.of(from.atDay(1), to.atEndOfMonth());
        List<Record> records = recordFinder.searchRecords(accountBookId, condition);
        return MonthlyTotalDataMaker.createMonthlyTotalData(records, from, to)
                .stream().map(data -> (StatisticsResponse) data).collect(Collectors.toList());
    }

}
