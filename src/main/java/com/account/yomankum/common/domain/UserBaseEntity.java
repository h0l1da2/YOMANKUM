package com.account.yomankum.common.domain;

import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@MappedSuperclass
public abstract class UserBaseEntity extends TimeBaseEntity {
    @CreatedBy
    private String createUser;
    @LastModifiedBy
    private String lastModifyingUser;

    public Long getCreateUserId(){
        return createUser != null ? Long.parseLong(createUser) : null;
    }

    public Long getLastModifyingUserId(){
        return lastModifyingUser != null ? Long.parseLong(lastModifyingUser) : null;
    }
}
