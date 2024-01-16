package com.account.yomankum.accountBook.domain;

import com.account.yomankum.accountBook.domain.record.Record;
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
import java.util.List;
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
public class AccountBook extends UserBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("accountBook_id")
    private Long id;
    private String name;
    private AccountBookType type;
    @OneToMany(mappedBy = "accountBook",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Record> records;

    public void updateName(String name) {
        this.name = name;
    }

    public void isAuthorityUser(Long sessionUserId) {
        if(!getCreateUserId().equals(sessionUserId)){
            throw new BadRequestException(Exception.ACCOUNT_BOOK_NOT_FOUND);
        }
    }

    public void addRecord(Record record) {
        records.add(record);
        record.appointAccountBook(this);
    }
}