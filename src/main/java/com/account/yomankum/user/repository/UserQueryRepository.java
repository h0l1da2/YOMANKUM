package com.account.yomankum.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.account.yomankum.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory query;


    public long updateUserPassword(String userEmail, String password) {
        return query.update(user)
                .set(user.password, password)
                .where(user.email.eq(userEmail))
                .execute();

    }
}
