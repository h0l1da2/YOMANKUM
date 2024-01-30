package com.account.yomankum.accountBook.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccountBook is a Querydsl query type for AccountBook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountBook extends EntityPathBase<AccountBook> {

    private static final long serialVersionUID = 1182605829L;

    public static final QAccountBook accountBook = new QAccountBook("accountBook");

    public final com.account.yomankum.common.domain.QUserBaseEntity _super = new com.account.yomankum.common.domain.QUserBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final StringPath createUser = _super.createUser;

    //inherited
    public final NumberPath<Long> createUserId = _super.createUserId;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    //inherited
    public final StringPath lastModifyingUser = _super.lastModifyingUser;

    //inherited
    public final NumberPath<Long> lastModifyingUserId = _super.lastModifyingUserId;

    public final StringPath name = createString("name");

    public final ListPath<com.account.yomankum.accountBook.domain.record.Record, com.account.yomankum.accountBook.domain.record.QRecord> records = this.<com.account.yomankum.accountBook.domain.record.Record, com.account.yomankum.accountBook.domain.record.QRecord>createList("records", com.account.yomankum.accountBook.domain.record.Record.class, com.account.yomankum.accountBook.domain.record.QRecord.class, PathInits.DIRECT2);

    public final EnumPath<AccountBookType> type = createEnum("type", AccountBookType.class);

    public QAccountBook(String variable) {
        super(AccountBook.class, forVariable(variable));
    }

    public QAccountBook(Path<? extends AccountBook> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountBook(PathMetadata metadata) {
        super(AccountBook.class, metadata);
    }

}

