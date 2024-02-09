package com.account.yomankum.statistics.controller;

import com.account.yomankum.statistics.vo.MonthlyTotal;
import com.account.yomankum.statistics.service.StatisticsService;
import com.account.yomankum.statistics.vo.TagRate;
import io.swagger.v3.oas.annotations.Operation;
import java.time.YearMonth;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/monthly/total/{accountBookId}")
    @Operation(summary = "월별 지출입 총합 통계")
    public List<MonthlyTotal> getMonthlyTotalData(@PathVariable Long accountBookId,
            @RequestParam YearMonth from,
            @RequestParam YearMonth to){
        return statisticsService.getMonthlyTotalData(accountBookId, from, to);
    }

    @GetMapping("/monthly/expenditure/majorTagRate/{accountBookId}")
    @Operation(summary = "한 달 지출의 대분류 비율 통계")
    public List<TagRate> getMonthlyExpenditureMajorTagRate(@PathVariable Long accountBookId,
            @RequestParam @NonNull YearMonth yearMonth){
        return statisticsService.getMonthlyExpenditureMajorTagRate(accountBookId, yearMonth);
    }

    @GetMapping("/monthly/income/majorTagRate/{accountBookId}")
    @Operation(summary = "한 달 수입의 대분류 비율 통계")
    public List<TagRate> getMonthlyIncomeMajorTagRate(@PathVariable Long accountBookId,
            @RequestParam @NonNull YearMonth yearMonth){
        return statisticsService.getMonthlyIncomeMajorTagRate(accountBookId, yearMonth);
    }


}
