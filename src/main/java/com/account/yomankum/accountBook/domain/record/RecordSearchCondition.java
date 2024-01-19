package com.account.yomankum.accountBook.domain.record;

import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.cglib.core.Local;

@Builder
public record RecordSearchCondition (
        String majorTag,
        String minorTag,
        String content,
        RecordType recordType,
        // 날짜범위
        LocalDateTime from,
        LocalDateTime to,
        // 사용 or 수입 내역 범위 검색
        Integer minMoney,
        Integer maxMoney,
        // 페이지네이션
        Integer page,
        Integer pageSize
) {}
