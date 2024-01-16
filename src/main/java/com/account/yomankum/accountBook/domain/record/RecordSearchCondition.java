package com.account.yomankum.accountBook.domain.record;

public class RecordSearchCondition {

    private String tag;
    private String customTag;
    private String description;

    // 사용 or 수입 내역 범위 검색
    private int minMoney;
    private int maxMoney;

    // 페이지네이션
    private int page;
    private int pageSize;

}
