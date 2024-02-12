package com.account.yomankum.statistics.service.impl.monthly;

import com.account.yomankum.statistics.service.StatisticsRequest;
import java.time.YearMonth;

public record MonthlyTotalStatisticRequest(

        Long accountBookId,
        YearMonth from,
        YearMonth to

)implements StatisticsRequest {}
