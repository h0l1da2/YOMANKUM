package com.account.yomankum.accountBook.dto.request;

import com.account.yomankum.accountBook.domain.record.RecordType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;

public record RecordUpdateRequest(
        @NotBlank
        @Size(min = 1, max = 30)
        String content,
        @NotBlank
        @Size(min = 1, max = 10)
        Long mainTagId,
        Set<String> subTags,
        RecordType recordType,
        int money,
        @DateTimeFormat(pattern = "yyyy-MM-dd EEE")
        LocalDate date) {
}
