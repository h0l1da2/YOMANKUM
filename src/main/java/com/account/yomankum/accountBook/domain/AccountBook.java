package com.account.yomankum.accountBook.domain;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.common.domain.UserBaseEntity;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.user.domain.User;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.expression.spel.ast.OpInc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        checkHasOwnerAuth(requesterId);
        this.name = name;
    }

    public void addRecord(Record record, Long requesterId) {
        checkHasGeneralAuth(requesterId);
        records.add(record);
        record.appointAccountBook(this);
    }

    public void deleteRecord(Record record, Long requesterId) {
        checkHasGeneralAuth(requesterId);
        records.remove(record);
    }

    public void deleteTag(Tag tag, Long requesterId) {
        checkHasGeneralAuth(requesterId);
        mainTags.remove(tag);
    }

    public void addTag(Tag tag, Long requesterId){
        checkHasGeneralAuth(requesterId);
        mainTags.add(tag);
        tag.assignAccountBook(this);
    }

    public void addAccountBookUser(User user, AccountBookRole accountBookRole, AccountBookUserStatus status) {
        AccountBookUser accountBookUser = new AccountBookUser(user, this, accountBookRole, status);
        accountBookUsers.add(accountBookUser);
        user.addAccountBook(accountBookUser);
    }

    public void removeUser(Long userId, Long requesterId) {
        if(!userId.equals(requesterId) && !isOwner(requesterId)){
            throw new BadRequestException(Exception.ACCESS_DENIED);
        }
        accountBookUsers.removeIf(user -> user.isUser(userId));
    }

    public void checkHasOwnerAuth(Long userId) {
        if(!isOwner(userId)){
            throw new BadRequestException(Exception.ACCESS_DENIED);
        }
    }

    public void checkHasGeneralAuth(Long userId){
        if(!isOwner(userId) && !isGeneralUser(userId)){
            throw new BadRequestException(Exception.ACCESS_DENIED);
        }
    }

    public void checkHasReadAuth(Long requesterId) {
        if(accountBookUsers.stream().filter(user -> user.isUser(requesterId)).findFirst().isEmpty()){
            throw new BadRequestException(Exception.ACCESS_DENIED);
        }
    }

    private boolean isOwner(Long userId){
        Optional<AccountBookUser> accountBookUserOptional = accountBookUsers.stream().filter(user -> user.isUser(userId)).findFirst();
        return accountBookUserOptional.isPresent() && accountBookUserOptional.get().isOwner();
    }

    private boolean isGeneralUser(Long userId){
        Optional<AccountBookUser> accountBookUserOptional = accountBookUsers.stream().filter(user -> user.isUser(userId)).findFirst();
        return accountBookUserOptional.isPresent() && accountBookUserOptional.get().isGeneralUser();
    }

    public void addTags(List<Tag> defaultTags) {
        this.mainTags.addAll(defaultTags);
        defaultTags.forEach(tag -> tag.assignAccountBook(this));
    }
}