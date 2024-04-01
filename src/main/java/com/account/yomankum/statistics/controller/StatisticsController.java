package com.account.yomankum.statistics.controller;

import com.account.yomankum.statistics.dto.StatisticsResponse;
import com.account.yomankum.statistics.service.StatisticsService;
import com.account.yomankum.statistics.service.impl.monthly.MonthlyTotalStatisticRequest;
import com.account.yomankum.statistics.service.impl.monthly.vo.MonthlyTotal;
import com.account.yomankum.statistics.service.impl.tagRate.mainTag.MainTagRateStatisticsRequest;
import com.account.yomankum.statistics.service.impl.tagRate.subTag.SubTagRateStatisticsRequest;
import com.account.yomankum.statistics.service.impl.tagRate.vo.TagRate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "월별 지출입 총합 통계", responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MonthlyTotal.class))))})
    public List<StatisticsResponse> getMonthlyTotalData(@Valid MonthlyTotalStatisticRequest request){
        return statisticsService.getMonthlyTotalData(request);
    }

    @GetMapping(value = "/monthly/expenditure/mainTagRate", produces = "application/json")
    @Operation(summary = "한 달 동안의 대분류 비율 통계", responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TagRate.class))))})
    public List<StatisticsResponse> getMonthlyExpenditureMainTagRate(@Valid MainTagRateStatisticsRequest request){
        return statisticsService.getMonthlyMainTagRate(request);
    }

    @GetMapping(value = "/monthly/expenditure/subTagRate", produces = "application/json")
    @Operation(summary = "(한 달 동안의) 하나의 대분류 내 소분류 비율", responses = {@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TagRate.class))))})
    public List<StatisticsResponse> getMonthlyExpenditureSubTagRate(@Valid SubTagRateStatisticsRequest request){
        return statisticsService.getMonthlySubTagRate(request);
    }

}
