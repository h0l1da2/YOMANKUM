package com.account.yomankum.statistics.controller;

import com.account.yomankum.statistics.dto.StatisticsResponse;
import com.account.yomankum.statistics.service.StatisticsService;
import com.account.yomankum.statistics.service.impl.monthly.MonthlyTotalStatisticRequest;
import com.account.yomankum.statistics.service.impl.tagRate.major.MajorTagRateStatisticsRequest;
import com.account.yomankum.statistics.service.impl.tagRate.minor.MinorTagRateStatisticsRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
@Tag(name = "Statistics", description = "통계 api")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping(value = "/monthly/total", produces = "application/json")
    public List<StatisticsResponse> getMonthlyTotalData(@Valid MonthlyTotalStatisticRequest request){
        return statisticsService.getMonthlyTotalData(request);
    }

    @GetMapping(value = "/monthly/expenditure/majorTagRate", produces = "application/json")
    public List<StatisticsResponse> getMonthlyExpenditureMajorTagRate(@Valid MajorTagRateStatisticsRequest request){
        return statisticsService.getMonthlyMajorTagRate(request);
    }

    @GetMapping(value = "/monthly/expenditure/minorTagRate", produces = "application/json")
    public List<StatisticsResponse> getMonthlyExpenditureMinorTagRate(@Valid MinorTagRateStatisticsRequest request){
        return statisticsService.getMonthlyMinorTagRate(request);
    }

}
