package com.account.yomankum.statistics.service.impl.tagRate.major;

import com.account.yomankum.accountBook.domain.record.RecordType;
import com.account.yomankum.statistics.service.StatisticsRequest;
import java.time.YearMonth;

public record MajorTagRateStatisticsRequest(

        Long accountBookId,
        YearMonth yearMonth,
        RecordType recordType

) implements StatisticsRequest {}
