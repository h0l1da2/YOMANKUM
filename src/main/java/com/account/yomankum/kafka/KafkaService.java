package com.account.yomankum.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaService {

    @KafkaListener(topics = "accountBook_input_love", groupId = "accountServiceConsumers")
    public void listenAccountNotifications(AccountBookInputNotice notice) {
        // 처리 로직
        System.out.println("Received Account Notification: " + notice.nickname());
    }
}

