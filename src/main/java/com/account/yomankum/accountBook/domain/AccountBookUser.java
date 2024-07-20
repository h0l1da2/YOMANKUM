package com.account.yomankum.accountBook.domain;

import com.account.yomankum.common.domain.UserBaseEntity;
import com.account.yomankum.user.domain.User;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountBookUser extends UserBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("userAccountBook_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "accountBook_id")
    private AccountBook accountBook;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private AccountBookRole accountBookRole;
    @Enumerated(EnumType.STRING)
    private AccountBookUserStatus status;

    public AccountBookUser(User user, AccountBook accountBook, AccountBookRole role, AccountBookUserStatus status){
        this.user = user;
        this.accountBook = accountBook;
        this.nickname = user.getNickname();
        this.accountBookRole = role;
        this.status = status;
    }

    public boolean isUser(Long userId){
        return user.getId().equals(userId);
    }

    public boolean isOwner() {
        return accountBookRole == AccountBookRole.OWNER;
    }

    public boolean isGeneralUser() {
        return accountBookRole == AccountBookRole.GENERAL;
    }
}
