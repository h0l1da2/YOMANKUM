package com.account.yomankum.statistics.service;

import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.statistics.dto.StatisticsResponse;
import com.account.yomankum.statistics.service.impl.monthly.MonthlyTotalStatisticRequest;
import com.account.yomankum.statistics.service.impl.monthly.vo.MonthlyTotal;
import com.account.yomankum.statistics.service.impl.tagRate.mainTag.MainTagRateStatisticsRequest;
import com.account.yomankum.statistics.service.impl.tagRate.subTag.SubTagRateStatisticsRequest;
import com.account.yomankum.statistics.service.impl.tagRate.vo.TagRate;
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

    public List<MonthlyTotal> getMonthlyTotalData(MonthlyTotalStatisticRequest request) {
        return services.get(StatisticsType.MONTHLY_TOTAL).getData(request).stream()
                .map(response -> (MonthlyTotal) response).collect(Collectors.toList());
    }

    public List<TagRate> getMonthlyMainTagRate(MainTagRateStatisticsRequest request) {
        return services.get(StatisticsType.MAIN_TAG_RATE).getData(request).stream()
                .map(response -> (TagRate) response).collect(Collectors.toList());
    }

    public List<TagRate> getMonthlySubTagRate(SubTagRateStatisticsRequest request) {
        return services.get(StatisticsType.SUB_TAG_RATE).getData(request).stream()
                .map(response -> (TagRate) response).collect(Collectors.toList());
    }

}
