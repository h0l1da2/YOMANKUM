package com.account.yomankum.common.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@MappedSuperclass
public abstract class UserBaseEntity extends TimeBaseEntity {
    @CreatedBy
    private String createUser;
    @LastModifiedBy
    private String lastModifyingUser;

    protected Long getCreateUserId(){
        return Long.parseLong(createUser);
    }

    protected Long getLastModifyingUserId(){
        return Long.parseLong(lastModifyingUser);
    }
}
