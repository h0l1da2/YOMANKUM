package com.account.yomankum.statistics.service;

import com.account.yomankum.statistics.dto.StatisticsResponse;
import com.account.yomankum.statistics.service.impl.monthly.MonthlyTotalStatisticRequest;
import com.account.yomankum.statistics.service.impl.tagRate.major.MajorTagRateStatisticsRequest;
import com.account.yomankum.statistics.service.impl.tagRate.minor.MinorTagRateStatisticsRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final Map<StatisticsType, StatisticsHandler> services;

    public StatisticsService(List<StatisticsHandler> services){
        this.services = services.stream()
                .collect(Collectors.toMap(StatisticsHandler::getSupportType, service -> service));
    }

    public List<StatisticsResponse> getMonthlyTotalData(MonthlyTotalStatisticRequest request) {
        return services.get(StatisticsType.MONTHLY_TOTAL).getData(request);
    }

    public List<StatisticsResponse> getMonthlyMajorTagRate(MajorTagRateStatisticsRequest request) {
        return services.get(StatisticsType.MAJOR_TAG_RATE).getData(request);
    }

    public List<StatisticsResponse> getMonthlyMinorTagRate(MinorTagRateStatisticsRequest request) {
        return services.get(StatisticsType.MINOR_TAG_RATE).getData(request);
    }

}
