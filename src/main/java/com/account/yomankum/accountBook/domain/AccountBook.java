package com.account.yomankum.accountBook.domain;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.common.domain.UserBaseEntity;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountBook extends UserBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("accountBook_id")
    private Long id;
    private String name;
    private AccountBookType type;
    @Default
    @OneToMany(mappedBy = "accountBook", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Record> records = new ArrayList<>();
    @Default
    @OneToMany(mappedBy = "accountBook", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Tag> mainTags = new ArrayList<>();
    @Default
    @OneToMany(mappedBy = "accountBook", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AccountBookUser> accountBookUsers = new ArrayList<>();

    public void updateName(String name, Long requesterId) {
        checkCreatedUser(requesterId);
        AccountBookRole accountBookRole = getAccountBookRole(requesterId);

        if (!accountBookRole.equals(AccountBookRole.OWNER)) {
            throw new BadRequestException(Exception.ACCESS_DENIED);
        }

        this.name = name;
    }

    public void addRecord(Record record, Long requesterId) {
        checkAuthorizedUser(requesterId);
        AccountBookRole accountBookRole = getAccountBookRole(requesterId);

        if (accountBookRole.equals(AccountBookRole.READ_ONLY) || accountBookRole.equals(AccountBookRole.GENERAL)) {
            throw new BadRequestException(Exception.ACCESS_DENIED);
        }

        records.add(record);
        record.appointAccountBook(this);
    }

    public void delete(Long requesterId) {
        checkCreatedUser(requesterId);
        for (AccountBookUser bookUser : accountBookUsers) {
            if (bookUser.getUser().getId().equals(requesterId)) {
                bookUser.getUser().getAccountBooks().remove(this);
                break;
            }
        }
        accountBookUsers.removeIf(accountBookUser -> accountBookUser.getUser().getId().equals(requesterId));
    }

    public AccountBookRole getAccountBookRole(Long requesterId) {
        checkAuthorizedUser(requesterId);
        return accountBookUsers.stream()
                .filter(accountBookUser -> accountBookUser.getUser().getId().equals(requesterId))
                .map(AccountBookUser::getAccountBookRole)
                .findFirst()
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));
    }

    public void deleteRecord(Record record, Long requesterId) {
        checkAuthorizedUser(requesterId);
        records.remove(record);
    }

    public void deleteTag(Tag tag, Long requesterId) {
        checkAuthorizedUser(requesterId);
        mainTags.remove(tag);
    }

    // 보안을 위해 '접근권한이 없음'이 아닌 '가계부가 없음' 메세지를 준다.
    public void checkAuthorizedUser(Long requesterId) {
        // 유저목록을 하나씩 돌면서 실제 있는 유저인지 확인한다.
        boolean checkUser = accountBookUsers.stream()
                .anyMatch(accountBookUser ->
                        accountBookUser.getUser().getId().equals(requesterId));
        if(!checkUser){
            throw new BadRequestException(Exception.ACCOUNT_BOOK_NOT_FOUND);
        }
    }

    private void checkCreatedUser(Long requesterId) {
        if (!this.getCreateUserId().equals(requesterId)) {
            throw new BadRequestException(Exception.ACCOUNT_BOOK_NOT_FOUND);
        }
    }

    public void addTag(Tag tag, Long requesterId){
        checkAuthorizedUser(requesterId);
        mainTags.add(tag);
        tag.assignAccountBook(this);
    }

    public void addTags(List<Tag> tags, Long requesterId) {
        tags.forEach(tag -> addTag(tag, requesterId));
    }

    public void addAccountBookUser(AccountBookUser accountBookUser) {
        accountBookUsers.add(accountBookUser);
    }

}