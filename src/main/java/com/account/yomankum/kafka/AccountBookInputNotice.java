package com.account.yomankum.kafka;

import java.time.LocalDateTime;

public record AccountBookInputNotice(
        Long id,
        String nickname, // 쓰기 시작하는 닉네임
        String event,
        LocalDateTime eventDatetime
) {

}
