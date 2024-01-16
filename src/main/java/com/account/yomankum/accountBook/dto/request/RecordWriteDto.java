package com.account.yomankum.accountBook.dto.request;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordWriteDto {

    @NotBlank
    @Size(min = 1, max = 30)
    private String content;
    @NotBlank
    @Size(min = 1, max = 10)
    private String majorTag;
    private List<String> minorTag;
    private RecordType recordType;
    private int money;
    @DateTimeFormat(pattern = "yyyy-MM-dd EEE")
    private LocalDateTime date;

    public Record toEntity(){
        return Record.builder()
                .content(content)
                .majorTag(majorTag)
                .minorTag(majorTag)
                .recordType(recordType)
                .money(money)
                .date(date)
                .build();
    }

}
