package com.account.yomankum.accountBook.domain.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecord is a Querydsl query type for Record
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecord extends EntityPathBase<Record> {

    private static final long serialVersionUID = 711083101L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecord record = new QRecord("record");

    public final com.account.yomankum.common.domain.QUserBaseEntity _super = new com.account.yomankum.common.domain.QUserBaseEntity(this);

    public final com.account.yomankum.accountBook.domain.QAccountBook accountBook;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final StringPath createUser = _super.createUser;

    //inherited
    public final NumberPath<Long> createUserId = _super.createUserId;

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    //inherited
    public final StringPath lastModifyingUser = _super.lastModifyingUser;

    //inherited
    public final NumberPath<Long> lastModifyingUserId = _super.lastModifyingUserId;

    public final StringPath majorTag = createString("majorTag");

    public final ListPath<String, StringPath> minorTag = this.<String, StringPath>createList("minorTag", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> money = createNumber("money", Integer.class);

    public final EnumPath<RecordType> recordType = createEnum("recordType", RecordType.class);

    public QRecord(String variable) {
        this(Record.class, forVariable(variable), INITS);
    }

    public QRecord(Path<? extends Record> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecord(PathMetadata metadata, PathInits inits) {
        this(Record.class, metadata, inits);
    }

    public QRecord(Class<? extends Record> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accountBook = inits.isInitialized("accountBook") ? new com.account.yomankum.accountBook.domain.QAccountBook(forProperty("accountBook")) : null;
    }

}

