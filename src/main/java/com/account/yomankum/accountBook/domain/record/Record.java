package com.account.yomankum.accountBook.domain.record;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.RecordUpdateRequest;
import com.account.yomankum.common.domain.UserBaseEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Set;
import jdk.jfr.Name;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Record extends UserBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("record_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountBook_id")
    private AccountBook accountBook;
    private String content;
    @ManyToOne
    private Tag mainTag; // 대분류
    @ElementCollection
    private Set<String> subTags; // 소분류
    @Enumerated(value =  EnumType.STRING)
    private RecordType recordType;
    private long amount;
    private LocalDate date;

    public void appointAccountBook(AccountBook accountBook) {
        this.accountBook = accountBook;
    }

    /*
      엔티티가 requestDto 를 알게 하는 것이 설계 관점에서는 지저분하게 보이나 코드 관리 측면에서 더 좋다고 판단하였음.
    */
    public void update(RecordUpdateRequest request, Tag tag, Long requesterId) {
        accountBook.checkAuthorizedUser(requesterId);
        this.content = request.content();
        this.mainTag = tag;
        this.subTags = request.subTags();
        this.recordType = request.recordType();
        this.amount = request.money();
        this.date = request.date();
    }

    public void delete(Long requesterId) {
        accountBook.deleteRecord(this, requesterId);
    }

    public void checkAuthorizedUser(Long userId){
        accountBook.checkAuthorizedUser(userId);
    }
}
