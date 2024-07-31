package com.account.yomankum.kafka;

import com.account.yomankum.auth.common.Auth;
import com.account.yomankum.auth.common.LoginUser;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.kafka.dto.AccountBookCreateNotice;
import com.account.yomankum.kafka.dto.AccountBookInputNotice;
import com.account.yomankum.kafka.dto.AccountBookUpdateNotice;
import com.account.yomankum.socket.common.CustomWebSocketHandler;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.service.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaService {

    private final UserFinder userFinder;
    private final CustomWebSocketHandler customWebSocketHandler;

    @KafkaListener(topics = "input", groupId = "accountBook")
    public void inputAccountBookNotification(AccountBookInputNotice notice) {
        customWebSocketHandler.sendAccountBookInputMessage(notice);
        log.info("[Kafka] input 메시지 수신 - accountBookId : {}", notice.accountBookId());
    }

    @KafkaListener(topics = "create", groupId = "accountBook")
    public void createAccountBookNotification(AccountBookCreateNotice notice) {
        // 알림 추가 -> Http ?
        log.info("[Kafka] create 메시지 수신 - accountBookId : {}", notice.accountBookId());
    }

    @KafkaListener(topics = "update", groupId = "accountBook")
    public void updateAccountBookNotification(@Auth LoginUser loginUser, AccountBookUpdateNotice notice) {
        User user = userFinder.findById(loginUser.getUserId())
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));

        boolean isExist = user.isUsersAccountBook(notice.accountBookId());

        if (isExist) {
            // 알림 추가 -> Http ?
            log.info("[Kafka] update 메시지 수신 - accountBookId : {}", notice.accountBookId());
        }
    }
}

