package com.account.yomankum.statistics.service;

public interface StatisticsService {

    StatisticsType getSupportType();
    Object get(StatisticsRequest request);

}
