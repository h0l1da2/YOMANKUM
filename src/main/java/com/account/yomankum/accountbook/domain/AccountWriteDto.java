package com.account.yomankum.accountbook.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountWriteDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd EEE")
    private LocalDateTime date;
    // 어떤 지출 내용인지
    @NotBlank
    @Size(min = 1, max = 30)
    private String usage;
    // 어떤 거에서 지출되는지 카드, 현금, 통장~? (서브통장, 생활비 통장 외... 선택지 O)
    @NotBlank
    @Size(min = 1, max = 10)
    private String payment;
    // 기본 태그 (추가 가능)
    @NotBlank
    @Size(min = 1, max = 10)
    private String tag;
    // 커스텀 태그 (추가 가능)
    @NotBlank
    @Size(min = 1, max = 10)
    private String customTag;
    @Size(min = 10)
    private int money;
}
