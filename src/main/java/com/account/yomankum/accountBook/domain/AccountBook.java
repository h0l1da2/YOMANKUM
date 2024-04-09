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
    @OneToMany(mappedBy = "accountBook",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Record> records = new ArrayList<>();
    @Default
    @OneToMany(mappedBy = "accountBook",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Tag> mainTags = new ArrayList<>();
    @Default
    @OneToMany(mappedBy = "accountBook",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<AccountBookUser> accountBookUsers = new ArrayList<>();

    public void updateName(String name, Long requesterId) {
        checkAuthorizedUser(requesterId);
        this.name = name;
    }

    public void addRecord(Record record, Long requesterId) {
        checkAuthorizedUser(requesterId);
        records.add(record);
        record.appointAccountBook(this);
    }

    public void delete(Long requesterId) {
        checkAuthorizedUser(requesterId);
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
        if(!getCreateUserId().equals(requesterId)){
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
}