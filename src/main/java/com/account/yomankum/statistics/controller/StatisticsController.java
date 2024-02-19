package com.account.yomankum.statistics.controller;

import com.account.yomankum.statistics.service.StatisticsService;
import com.account.yomankum.statistics.service.StatisticsType;
import com.account.yomankum.statistics.service.impl.monthly.MonthlyTotalStatisticRequest;
import com.account.yomankum.statistics.service.impl.monthly.vo.MonthlyTotal;
import com.account.yomankum.statistics.service.impl.tagRate.major.MajorTagRateStatisticsRequest;
import com.account.yomankum.statistics.service.impl.tagRate.minor.MinorTagRateStatisticsRequest;
import com.account.yomankum.statistics.service.impl.tagRate.vo.TagRate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.awt.print.Book;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
@Tag(name = "Statistics", description = "통계 api")
public class StatisticsController {

    private final Map<StatisticsType, StatisticsService> services;

    public StatisticsController(List<StatisticsService> services){
        this.services = services.stream()
                .collect(Collectors.toMap(StatisticsService::getSupportType, service -> service));
    }

    @GetMapping("/monthly/total")
    @Operation(summary = "월별 지출입 총합 통계", responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MonthlyTotal.class))))})
    public Object getMonthlyTotalData(@Valid MonthlyTotalStatisticRequest request){
        return services.get(StatisticsType.MONTHLY_TOTAL).get(request);
    }

    @GetMapping("/monthly/expenditure/majorTagRate")
    @Operation(summary = "한 달 동안의 대분류 비율 통계", responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TagRate.class))))})
    public Object getMonthlyExpenditureMajorTagRate(@Valid MajorTagRateStatisticsRequest request){
        return services.get(StatisticsType.MAJOR_TAG_RATE).get(request);
    }

    @GetMapping("/monthly/expenditure/minorTagRate")
    @Operation(summary = "(한 달 동안의) 하나의 대분류 내 소분류 비율", responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TagRate.class))))})
    public Object getMonthlyExpenditureMinorTagRate(@Valid MinorTagRateStatisticsRequest request){
        return services.get(StatisticsType.MINOR_TAG_RATE).get(request);
    }

}
