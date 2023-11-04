package com.account.yomankum.domain.dto;

import com.account.yomankum.domain.Payment;
import com.account.yomankum.domain.Tag;
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
    private String usage;
    // 어떤 거에서 지출되는지 카드, 현금, 통장~? (서브통장, 생활비 통장 외... 선택지 O)
    private Payment payment;
    // 기본 태그 (추가 가능)
    private Tag tag;
    // 커스텀 태그 (추가 가능)
    private String customTag;
}
