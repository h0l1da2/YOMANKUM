package com.account.yomankum.security.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class CustomOAuth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    private final String SNS_REQUEST_URI = "/oauth2/authorization/";
    private final String BASE_URL = "http://localhost:8080/";
    private SnsInfo snsInfo;

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith(SNS_REQUEST_URI)) {
            String sns = request.getRequestURI().substring(SNS_REQUEST_URI.length());
            return this.resolve(request, sns);
        }
        return null;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        log.info("리졸버 시작");

        String sns = clientRegistrationId.toUpperCase();
        String state = UUID.randomUUID().toString();

        setStateSession(request, state);
        setClientSession(request, state, sns);

        String clientId = snsInfo.clientId(sns);
        String authUri = snsInfo.authUri(sns);
        String redirectUri = "/";
        String responseType = snsInfo.responseType(); // code
        String[] scopes = snsInfo.scope(sns).stream().toArray(String[]::new);

        Map<String, String> clientMap = new HashMap<>();
        clientMap.put(OAuth2ParameterNames.RESPONSE_TYPE, responseType);
        clientMap.put("client", sns);

        return OAuth2AuthorizationRequest
                .authorizationCode()
                .clientId(clientId)
                .authorizationUri(authUri)
                .scope(scopes)
                .redirectUri(redirectUri)
                .state(state)
                .parameters(params -> params.putAll(clientMap))
                .build();
    }

    private void setClientSession(HttpServletRequest request, String state, String sns) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.setAttribute(state, sns);
        }
    }

    private void setStateSession(HttpServletRequest request, String state) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.setAttribute("state", state);
        }
    }

}
