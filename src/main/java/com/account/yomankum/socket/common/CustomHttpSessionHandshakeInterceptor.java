package com.account.yomankum.socket.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

public class CustomHttpSessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        String userId = httpReq.getParameter("userId");

        if (StringUtils.hasText(userId)) {
            attributes.put("userId", userId);
        }

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

}
