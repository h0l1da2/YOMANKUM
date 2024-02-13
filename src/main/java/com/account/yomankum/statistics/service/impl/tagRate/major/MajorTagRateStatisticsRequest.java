package com.account.yomankum.statistics.service.impl.tagRate.major;

import com.account.yomankum.accountBook.domain.record.RecordType;
import com.account.yomankum.statistics.service.StatisticsRequest;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;

public record MajorTagRateStatisticsRequest(

        @NotNull Long accountBookId,
        @NotNull YearMonth yearMonth,
        @NotNull RecordType recordType

) implements StatisticsRequest {}
