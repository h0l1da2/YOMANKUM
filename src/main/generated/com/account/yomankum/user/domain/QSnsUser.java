package com.account.yomankum.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSnsUser is a Querydsl query type for SnsUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSnsUser extends EntityPathBase<SnsUser> {

    private static final long serialVersionUID = -1552159269L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSnsUser snsUser = new QSnsUser("snsUser");

    public final DateTimePath<java.util.Date> birthday = createDateTime("birthday", java.util.Date.class);

    public final StringPath email = createString("email");

    public final EnumPath<com.account.yomankum.user.domain.type.Gender> gender = createEnum("gender", com.account.yomankum.user.domain.type.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.account.yomankum.user.domain.type.Job> job = createEnum("job", com.account.yomankum.user.domain.type.Job.class);

    public final DateTimePath<java.time.LocalDateTime> joinDate = createDateTime("joinDate", java.time.LocalDateTime.class);

    public final StringPath nickname = createString("nickname");

    public final DateTimePath<java.time.LocalDateTime> pwdChangeDate = createDateTime("pwdChangeDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> removeDate = createDateTime("removeDate", java.time.LocalDateTime.class);

    public final QRole role;

    public final EnumPath<com.account.yomankum.security.oauth.type.Sns> sns = createEnum("sns", com.account.yomankum.security.oauth.type.Sns.class);

    public final DateTimePath<java.time.LocalDateTime> stopDate = createDateTime("stopDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> token = createDateTime("token", java.time.LocalDateTime.class);

    public final StringPath uuidKey = createString("uuidKey");

    public QSnsUser(String variable) {
        this(SnsUser.class, forVariable(variable), INITS);
    }

    public QSnsUser(Path<? extends SnsUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSnsUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSnsUser(PathMetadata metadata, PathInits inits) {
        this(SnsUser.class, metadata, inits);
    }

    public QSnsUser(Class<? extends SnsUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role")) : null;
    }

}

