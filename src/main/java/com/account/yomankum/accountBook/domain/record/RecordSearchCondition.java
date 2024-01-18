package com.account.yomankum.accountBook.domain.record;

public record RecordSearchCondition (
        String majorTag,
        String minorTag,
        String content,
        // 사용 or 수입 내역 범위 검색
        Integer minMoney,
        Integer maxMoney,
        // 페이지네이션
        Integer page,
        Integer pageSize
) {}
