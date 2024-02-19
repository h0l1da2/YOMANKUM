package com.account.yomankum.statistics.service.impl.monthly;

import com.account.yomankum.statistics.service.StatisticsRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;

public record MonthlyTotalStatisticRequest(

        @NotNull
        @Schema(description = "가계부 id", example = "11")
        Long accountBookId,
        @NotNull
        @Schema(description = "시작", example = "2023-01")
        YearMonth from,
        @NotNull
        @Schema(description = "끝", example = "2023-12")
        YearMonth to

)implements StatisticsRequest {}
