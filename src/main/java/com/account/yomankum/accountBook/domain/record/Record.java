package com.account.yomankum.accountBook.domain.record;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Long id;
    private String content;
    private String payment;
    private String tag; // 대분류
    private String customTag; // 소분류
    @Enumerated(value =  EnumType.STRING)
    private RecordType recordType;
    private int money;
    private LocalDateTime date;

}
