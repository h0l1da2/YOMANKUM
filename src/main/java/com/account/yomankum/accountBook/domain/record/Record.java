package com.account.yomankum.accountBook.domain.record;

import com.account.yomankum.accountBook.domain.AccountBook;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jdk.jfr.Name;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Record {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("record_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "accountBook_id")
    private AccountBook accountBook;
    private String content;
    private String majorTag; // 대분류
    private String minorTag; // 소분류
    @Enumerated(value =  EnumType.STRING)
    private RecordType recordType;
    private int money;
    private LocalDateTime date;

    // TODO. 적절한 메소드 명 없는지?
    public void appointAccountBook(AccountBook accountBook) {
        this.accountBook = accountBook;
    }
}
