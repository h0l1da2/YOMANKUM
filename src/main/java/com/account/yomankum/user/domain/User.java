package com.account.yomankum.user.domain;

import com.account.yomankum.accountBook.domain.AccountBookUser;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.user.domain.type.Gender;
import com.account.yomankum.user.dto.request.FirstLoginUserInfoSaveDto;
import com.account.yomankum.user.dto.request.UserInfoUpdateDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Role role;
    private String nickname;

    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private String job;
    private Integer salary;

    @Builder.Default
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<AccountBookUser> accountBooks = new ArrayList<>();

    private Instant joinDatetime;
    private Instant lastLoginDatetime; // 마지막 로그인이 NULL 일 경우, 첫 로그인
    private Instant stopDatetime;
    private Instant pwdChangeDatetime;
    private Instant removeDatetime;

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateLastLoginDatetime() {
        this.lastLoginDatetime = Instant.now();
    }

    public void updateFirstUserInfo(FirstLoginUserInfoSaveDto dto) {
        this.nickname = dto.nickname();
        this.gender = dto.gender();
        this.birthday = dto.birthDate();
    }

    public void updateUserInfo(UserInfoUpdateDto dto) {
        this.gender = dto.gender();
        this.birthday = dto.birthDate();
        this.job = dto.job();
        this.salary = dto.salary();
    }

    // 보안을 위해 '접근권한이 없음'이 아닌 '가계부가 없음' 메세지를 준다.
    public void checkAuthorizedUser(Long requesterId) {
        if(!id.equals(requesterId)){
            throw new BadRequestException(Exception.USER_NOT_FOUND);
        }
    }

    public void addAccountBook(AccountBookUser accountBookUser) {
        checkAuthorizedUser(accountBookUser.getUser().getId());
        accountBooks.add(accountBookUser);
    }
}
