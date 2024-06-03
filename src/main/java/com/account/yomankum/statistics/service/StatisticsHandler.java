package com.account.yomankum.statistics.service;

import com.account.yomankum.statistics.dto.StatisticsRequest;
import com.account.yomankum.statistics.dto.StatisticsResponse;
import java.util.List;

public interface StatisticsHandler {

    StatisticsType getSupportType();
    List<StatisticsResponse> getData(StatisticsRequest request, Long requesterId);

}
