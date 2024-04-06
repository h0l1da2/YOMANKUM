package com.account.yomankum.accountBook.dto.request;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordType;
import com.account.yomankum.accountBook.domain.tag.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;

public record RecordCreateRequest (
        @NotBlank
        @Size(min = 1, max = 30)
        String content,
        @NotBlank
        @Size(min = 1, max = 10)
        Long mainTagId,
        Set<String> subTags,
        RecordType recordType,
        long amount,
        @DateTimeFormat(pattern = "yyyy-MM-dd EEE")
        LocalDate date
){
    public Record toEntity(Tag tag){
        return Record.builder()
                .content(content)
                .mainTag(tag)
                .subTags(subTags)
                .recordType(recordType)
                .amount(amount)
                .date(date)
                .build();
    }
}
