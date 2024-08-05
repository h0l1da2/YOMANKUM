package com.account.yomankum.socket.common;

import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.socket.dto.AccountBookWebSocketNotice;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.service.UserFinder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomWebSocketHandler extends TextWebSocketHandler {
    // 소켓에 연결되어있는 세션 목록
    private final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final Map<Long, WebSocketSession> userIdSessionMap = new ConcurrentHashMap<>();

    private final UserFinder userFinder;
    private final ObjectMapper mapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        String userId = (String) session.getAttributes().get("userId");

        if (StringUtils.hasText(userId)) {
            userIdSessionMap.put(Long.parseLong(userId), session);
        }

        log.info("소켓 연결 성공 - userID : {}", userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        String userId = (String) session.getAttributes().get("userId");

        if (StringUtils.hasText(userId)) {
            userIdSessionMap.remove(Long.parseLong(userId), session);
        }

        log.info("소켓 연결 종료 - userID : {}", userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // message 받으면..
    }

    // 클라이언트에게 메시지 보내기
    public void sendAccountBookMessage(AccountBookWebSocketNotice notice) {
        List<Long> userIdList = new ArrayList<>(userIdSessionMap.keySet());
        List<User> userList = userFinder.findAllById(userIdList);

        Long accountBookId = notice.accountBookId();
        List<Long> accountBookUserIdList = userList.stream()
                .filter(user -> user.isUsersAccountBook(accountBookId))
                .map(User::getId)
                .toList();

        accountBookUserIdList.forEach(userId -> {
            WebSocketSession session = userIdSessionMap.get(userId);

            if (session != null && session.isOpen()) {
                try {
                    String message = mapper.writeValueAsString(notice);
                    session.sendMessage(new TextMessage(message));
                } catch (JsonProcessingException e) {
                    log.error("JSON 파싱 에러");
                    throw new BadRequestException(Exception.SERVER_ERROR);
                } catch (IOException e) {
                    log.error("세션 메시지 전송 에러");
                    throw new BadRequestException(Exception.SERVER_ERROR);
                }
            }
        });

    }

}
