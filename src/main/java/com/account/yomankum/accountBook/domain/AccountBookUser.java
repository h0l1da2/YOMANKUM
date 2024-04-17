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
    private UserStatus status;
}
