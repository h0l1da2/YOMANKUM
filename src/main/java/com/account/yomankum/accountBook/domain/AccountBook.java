package com.account.yomankum.accountBook.domain;

import com.account.yomankum.common.domain.UserBaseEntity;
import com.account.yomankum.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.nio.file.AccessDeniedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountBook extends UserBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private AccountBookType type;

    public void updateName(Long userId, String name) {
        if (!userId.equals(getCreateUserId())) {
            throw new RuntimeException("user has no authority");
        }
        this.name = name;
    }

    public void delete(Long userId) {
        if (!userId.equals(getCreateUserId())) {
            throw new RuntimeException("user has no authority");
        }
    }
}