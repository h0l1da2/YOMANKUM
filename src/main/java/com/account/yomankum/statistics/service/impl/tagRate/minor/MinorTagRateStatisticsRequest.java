package com.account.yomankum.statistics.service.impl.tagRate.minor;

import com.account.yomankum.accountBook.domain.record.RecordType;
import com.account.yomankum.statistics.service.StatisticsRequest;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;

public record MinorTagRateStatisticsRequest (

        @NotNull Long accountBookId,
        @NotNull String majorTag,
        @NotNull YearMonth yearMonth,
        @NotNull RecordType recordType

) implements StatisticsRequest {}
