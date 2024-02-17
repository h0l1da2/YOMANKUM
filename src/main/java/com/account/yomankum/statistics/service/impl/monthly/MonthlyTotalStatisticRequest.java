package com.account.yomankum.statistics.service.impl.monthly;

import com.account.yomankum.statistics.service.StatisticsRequest;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;

public record MonthlyTotalStatisticRequest(

        @NotNull Long accountBookId,
        @NotNull YearMonth from,
        @NotNull YearMonth to

)implements StatisticsRequest {}
