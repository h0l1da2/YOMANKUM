package com.account.yomankum.statistics.controller;

import com.account.yomankum.statistics.service.StatisticsService;
import com.account.yomankum.statistics.service.StatisticsType;
import com.account.yomankum.statistics.service.impl.monthly.MonthlyTotalStatisticRequest;
import com.account.yomankum.statistics.service.impl.tagRate.major.MajorTagRateStatisticsRequest;
import com.account.yomankum.statistics.service.impl.tagRate.minor.MinorTagRateStatisticsRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    private final Map<StatisticsType, StatisticsService> services;

    public StatisticsController(List<StatisticsService> services){
        this.services = services.stream()
                .collect(Collectors.toMap(StatisticsService::getSupportType, service -> service));
    }

    @GetMapping("/monthly/total")
    @Operation(summary = "월별 지출입 총합 통계")
    public Object getMonthlyTotalData(@Valid MonthlyTotalStatisticRequest request){
        return services.get(StatisticsType.MONTHLY_TOTAL).get(request);
    }

    @GetMapping("/monthly/expenditure/majorTagRate")
    @Operation(summary = "한 달 동안의 대분류 비율 통계")
    public Object getMonthlyExpenditureMajorTagRate(@Valid MajorTagRateStatisticsRequest request){
        return services.get(StatisticsType.MAJOR_TAG_RATE).get(request);
    }

    @GetMapping("/monthly/expenditure/minorTagRate")
    @Operation(summary = "(한 달 동안의) 하나의 대분류 내 소분류 비율")
    public Object getMonthlyExpenditureMinorTagRate(@Valid MinorTagRateStatisticsRequest request){
        return services.get(StatisticsType.MINOR_TAG_RATE).get(request);
    }

}
