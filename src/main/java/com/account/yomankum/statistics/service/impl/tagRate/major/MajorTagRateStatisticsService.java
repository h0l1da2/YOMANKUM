package com.account.yomankum.statistics.service.impl.tagRate.major;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import com.account.yomankum.accountBook.domain.record.RecordType;
import com.account.yomankum.accountBook.service.RecordFinder;
import com.account.yomankum.statistics.service.StatisticsRequest;
import com.account.yomankum.statistics.service.StatisticsService;
import com.account.yomankum.statistics.service.StatisticsType;
import com.account.yomankum.statistics.service.impl.tagRate.vo.TagRate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MajorTagRateStatisticsService implements StatisticsService {

    private final RecordFinder recordFinder;

    @Override
    public StatisticsType getSupportType() {
        return StatisticsType.MAJOR_TAG_RATE;
    }

    @Override
    public Object get(StatisticsRequest request) {
        MajorTagRateStatisticsRequest param = (MajorTagRateStatisticsRequest) request;
        YearMonth yearMonth = param.yearMonth();
        RecordType recordType = param.recordType();
        Long accountBookId = param.accountBookId();

        RecordSearchCondition condition = RecordSearchCondition.of(yearMonth, recordType);
        List<Record> records = recordFinder.searchRecords(accountBookId, condition);
        return MajorTagRateDataMaker.createMajorTagRateData(records);
    }

}
