package com.account.yomankum.kafka.dto;

import java.time.LocalDateTime;

public record AccountBookCreateNotice(
        Long accountBookId,
        String nickname, // 쓰기 시작하는 닉네임
        LocalDateTime accountBookCreatedAt,
        LocalDateTime eventCreatedAt
) {
}
