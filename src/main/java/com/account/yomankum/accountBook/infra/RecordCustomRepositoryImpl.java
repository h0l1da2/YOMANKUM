package com.account.yomankum.accountBook.infra;

import static com.account.yomankum.accountBook.domain.record.QRecord.*;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordCustomRepository;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
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
                .limit(condition.pageSize())
                .fetch();
    }

    private long getOffset(RecordSearchCondition condition) {
        return condition.page() != null && condition.pageSize() != null ?
                (long) (condition.page() - 1) * condition.pageSize() : 0;
    }

    private BooleanBuilder allCondition(RecordSearchCondition condition){
        return new BooleanBuilder()
                .and(majorTagContains(condition.majorTag()))
                .and(minorTagContains(condition.majorTag()))
                .and(contentContains(condition.content()))
                .and(moneyRange(condition.minMoney(), condition.maxMoney()));
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

}
