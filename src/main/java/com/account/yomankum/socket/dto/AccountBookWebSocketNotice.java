package com.account.yomankum.socket.dto;

import com.account.yomankum.kafka.dto.AccountBookInputNotice;
import lombok.Builder;

@Builder
public record AccountBookWebSocketNotice(
        Long accountBookId,
        String nickname,
        EventType eventType
) {
    public static AccountBookWebSocketNotice from(AccountBookInputNotice notice) {
        return AccountBookWebSocketNotice.builder()
                .accountBookId(notice.accountBookId())
                .eventType(EventType.ACCOUNT_BOOK_INPUT)
                .nickname(notice.nickname())
                .build();
    }
}
