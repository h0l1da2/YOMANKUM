package com.account.yomankum.accountBook.domain.record;

import java.time.LocalDate;
import java.time.YearMonth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordSearchCondition{

    private Long mainTagId;
    private String mainTagName;
    private String subTagName;
    private String content;
    private RecordType recordType;
    // 날짜범위
    private LocalDate from;
    private LocalDate to;
    // 사용 or 수입 내역 범위 검색
    private Integer minMoney;
    private Integer maxMoney;
    // 페이지네이션
    private Integer page;
    private Integer pageSize;

    public static RecordSearchCondition of(LocalDate from, LocalDate to){
        RecordSearchCondition condition = new RecordSearchCondition();
        condition.from = from;
        condition.to = to;
        return condition;
    }

    public static RecordSearchCondition of(YearMonth yearMonth, RecordType recordType){
        LocalDate from = yearMonth.atDay(1);
        LocalDate to = yearMonth.atEndOfMonth();
        RecordSearchCondition condition = of(from, to);
        condition.recordType = recordType;
        return condition;
    }

    public static RecordSearchCondition of(YearMonth yearMonth, RecordType recordType, Long mainTagId){
        RecordSearchCondition condition = of(yearMonth,recordType);
        condition.mainTagId = mainTagId;
        return condition;
    }

}
