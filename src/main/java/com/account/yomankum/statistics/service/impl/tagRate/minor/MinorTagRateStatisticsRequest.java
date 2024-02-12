package com.account.yomankum.statistics.service.impl.tagRate.minor;

import com.account.yomankum.accountBook.domain.record.RecordType;
import com.account.yomankum.statistics.service.StatisticsRequest;
import java.time.YearMonth;

public record MinorTagRateStatisticsRequest (

        Long accountBookId,
        String majorTag,
        YearMonth yearMonth,
        RecordType recordType

) implements StatisticsRequest {}
