package com.account.yomankum.statistics.service.impl.tagRate.minor;

import com.account.yomankum.accountBook.domain.record.RecordType;
import com.account.yomankum.statistics.dto.StatisticsRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;

public record MinorTagRateStatisticsRequest (

        @NotNull
        @Schema(description = "가계부 id", example = "11")
        Long accountBookId,
        @NotNull
        @Schema(description = "대분류", example = "식사")
        String majorTag,
        @NotNull
        @Schema(description = "년도-달", example = "2023-12")
        YearMonth yearMonth,
        @NotNull
        @Schema(example = "EXPENDITURE")
        RecordType recordType

) implements StatisticsRequest {}
