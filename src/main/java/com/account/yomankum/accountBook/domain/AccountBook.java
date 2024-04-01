package com.account.yomankum.accountBook.domain;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.common.domain.UserBaseEntity;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
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

    public void addTags(List<Tag> defaultTags) {
        mainTags.addAll(defaultTags);
        defaultTags.forEach(tag -> tag.assignAccountBook(this));
    }
}