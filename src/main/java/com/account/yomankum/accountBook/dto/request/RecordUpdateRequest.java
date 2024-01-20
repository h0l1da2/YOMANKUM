package com.account.yomankum.accountBook.dto.request;

import com.account.yomankum.accountBook.domain.record.RecordType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public record RecordUpdateRequest(
        @NotBlank
        @Size(min = 1, max = 30)
        String content,
        @NotBlank
        @Size(min = 1, max = 10)
        String majorTag,
        List<String> minorTag,
        RecordType recordType,
        int money,
        @DateTimeFormat(pattern = "yyyy-MM-dd EEE")
        LocalDateTime date) {
}
