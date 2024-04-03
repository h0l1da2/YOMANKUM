package com.account.yomankum.accountBook.domain.tag;

import com.account.yomankum.accountBook.domain.AccountBook;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountBook_id")
    private AccountBook accountBook;
    @Embedded
    private Color color;

    public Tag() {
    }

    public static Tag of(String name) {
        Tag tag = new Tag();
        tag.name = name;
        return tag;
    }

    public static Tag of(String name, Color color) {
        Tag tag = of(name);
        tag.color = color;
        return tag;
    }

    public void assignAccountBook(AccountBook accountBook) {
        this.accountBook = accountBook;
    }

    public void update(String name, Long requesterId) {
        accountBook.checkAuthorizedUser(requesterId);
        this.name = name;
    }

    public void delete(Long sessionUserId) {
        accountBook.deleteTag(this, sessionUserId);
    }

}