package com.account.yomankum.statistics.controller;
import com.account.yomankum.common.AbstractRestDocsTests;
import com.account.yomankum.statistics.dto.StatisticsResponse;
import com.account.yomankum.statistics.service.StatisticsService;
import com.account.yomankum.statistics.service.impl.monthly.MonthlyTotalStatisticRequest;
import com.account.yomankum.statistics.service.impl.monthly.vo.MonthlyTotal;
import com.account.yomankum.statistics.service.impl.tagRate.mainTag.MainTagRateStatisticsRequest;
import com.account.yomankum.statistics.service.impl.tagRate.subTag.SubTagRateStatisticsRequest;
import com.account.yomankum.statistics.service.impl.tagRate.vo.TagRate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.YearMonth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest extends AbstractRestDocsTests {

    @MockBean
    private StatisticsService statisticsService;

    @Test
    @WithMockUser
    public void getMonthlyTotalDataTest() throws Exception {
        List<StatisticsResponse> sampleResponse = List.of(new MonthlyTotal(YearMonth.of(2023,1), 410_000L, 250_000L));
        given(this.statisticsService.getMonthlyTotalData(any(MonthlyTotalStatisticRequest.class))).willReturn((sampleResponse));

        mockMvc.perform(get("/api/v1/statistics/monthly/total")
                        .param("accountBookId", "11")
                        .param("from", "2023-01")
                        .param("to", "2023-12")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'yearMonth': '2023-01', 'totalIncome': 410000, 'totalExpenditure': 250000}]"));
    }

    @Test
    @WithMockUser
    public void getMonthlyExpenditureMainTagRate() throws Exception {
        List<StatisticsResponse> sampleResponse = List.of(new TagRate("식사", 500_000L, 250_000L));
        given(this.statisticsService.getMonthlyMainTagRate(any(MainTagRateStatisticsRequest.class))).willReturn((sampleResponse));

        mockMvc.perform(get("/api/v1/statistics/monthly/expenditure/mainTagRate")
                        .param("accountBookId", "11")
                        .param("yearMonth", "2023-01")
                        .param("recordType", "EXPENDITURE")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'tag': '식사', 'rate': 50, 'money': 250000}]"));
    }

    @Test
    @WithMockUser
    public void getMonthlyExpenditureSubTagRate() throws Exception {
        List<StatisticsResponse> sampleResponse = List.of(new TagRate("보너스", 5_000_000L, 500_000L));
        given(this.statisticsService.getMonthlySubTagRate(any(SubTagRateStatisticsRequest.class))).willReturn((sampleResponse));

        mockMvc.perform(get("/api/v1/statistics/monthly/expenditure/subTagRate")
                        .param("accountBookId", "11")
                        .param("tagName", "월급")
                        .param("yearMonth", "2023-01")
                        .param("recordType", "INCOME")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'tag': '보너스', 'rate': 10, 'money': 500000}]"));
    }

}