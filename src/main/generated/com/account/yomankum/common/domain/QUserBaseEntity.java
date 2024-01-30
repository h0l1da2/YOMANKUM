package com.account.yomankum.common.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserBaseEntity is a Querydsl query type for UserBaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QUserBaseEntity extends EntityPathBase<UserBaseEntity> {

    private static final long serialVersionUID = -745985369L;

    public static final QUserBaseEntity userBaseEntity = new QUserBaseEntity("userBaseEntity");

    public final QTimeBaseEntity _super = new QTimeBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath createUser = createString("createUser");

    public final NumberPath<Long> createUserId = createNumber("createUserId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath lastModifyingUser = createString("lastModifyingUser");

    public final NumberPath<Long> lastModifyingUserId = createNumber("lastModifyingUserId", Long.class);

    public QUserBaseEntity(String variable) {
        super(UserBaseEntity.class, forVariable(variable));
    }

    public QUserBaseEntity(Path<? extends UserBaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserBaseEntity(PathMetadata metadata) {
        super(UserBaseEntity.class, metadata);
    }

}

