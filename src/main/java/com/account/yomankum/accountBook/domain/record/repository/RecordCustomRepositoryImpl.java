package com.account.yomankum.accountBook.domain.record.repository;

import static com.account.yomankum.accountBook.domain.record.QRecord.*;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import com.account.yomankum.accountBook.domain.record.RecordType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class RecordCustomRepositoryImpl implements RecordCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Record> searchRecords(Long accountBookId, RecordSearchCondition condition) {
        return queryFactory
                .selectFrom(record)
                .where(record.accountBook.id.eq(accountBookId)
                        .and(allCondition(condition)))
                .offset(getOffset(condition))
                .limit(condition.getPageSize())
                .fetch();
    }

    private long getOffset(RecordSearchCondition condition) {
        return condition.getPage() != null && condition.getPageSize() != null ?
                (long) (condition.getPage() - 1) * condition.getPageSize() : 0;
    }

    private BooleanBuilder allCondition(RecordSearchCondition condition){
        return new BooleanBuilder()
                .and(majorTagContains(condition.getMajorTag()))
                .and(minorTagContains(condition.getMinorTag()))
                .and(contentContains(condition.getContent()))
                .and(recordTypeEquals(condition.getRecordType()))
                .and(date(condition.getFrom(), condition.getTo()))
                .and(moneyRange(condition.getMinMoney(), condition.getMaxMoney()));
    }

    private BooleanExpression majorTagContains(String tag){
        return StringUtils.hasText(tag)?
                record.majorTag.contains(tag) : null;
    }

    private BooleanExpression minorTagContains(String tag) {
        return StringUtils.hasText(tag)?
                record.minorTag.contains(tag) : null;
    }

    private BooleanExpression contentContains(String content) {
        return StringUtils.hasText(content)?
                record.content.contains(content) : null;
    }

    private BooleanExpression moneyRange(Integer min, Integer max) {
        BooleanExpression moneyGoe = min != null ? record.money.goe(min) : null;
        BooleanExpression moneyLoe = max != null ? record.money.loe(max) : null;

        return moneyGoe != null ? moneyGoe.and(moneyLoe) : moneyLoe;
    }

    private BooleanExpression recordTypeEquals(RecordType recordType) {
        return recordType != null ?
                record.recordType.eq(recordType) : null;
    }

    private BooleanExpression date(LocalDate from, LocalDate to) {
        BooleanExpression goeFrom = from != null ? record.date.goe(from) : null;
        BooleanExpression loeTo =  to != null ? record.date.loe(to) : null;

        return goeFrom != null ? goeFrom.and(loeTo) : loeTo;
    }

}
