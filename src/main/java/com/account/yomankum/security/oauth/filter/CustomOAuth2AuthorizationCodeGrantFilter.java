package com.account.yomankum.security.oauth.filter;

import com.account.yomankum.security.oauth.response.TokenResponse;
import com.account.yomankum.security.oauth.type.Sns;
import com.account.yomankum.security.oauth.type.TokenProp;
import com.account.yomankum.security.oauth.user.SnsInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationCodeGrantFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.valueOf;

@Slf4j
public class CustomOAuth2AuthorizationCodeGrantFilter extends OAuth2AuthorizationCodeGrantFilter {

    private final SnsInfo snsInfo;

    public CustomOAuth2AuthorizationCodeGrantFilter(final ClientRegistrationRepository clientRegistrationRepository, final OAuth2AuthorizedClientRepository authorizedClientRepository, final AuthenticationManager authenticationManager, final SnsInfo snsInfo) {
        super(clientRegistrationRepository, authorizedClientRepository, authenticationManager);
        this.snsInfo = snsInfo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // https://localhost:8080?code={code}&state={state}
        String code = request.getParameter(TokenProp.CODE.getName());

        if (!StringUtils.hasText(code)) {
            log.error("코드 없음");
            filterChain.doFilter(request, response);
            return;
        }

        String requestURI = request.getRequestURL().toString();
        HttpSession session = request.getSession(false);

        if (session != null) {
            String myState = String.valueOf(
                    session.getAttribute(TokenProp.STATE.getName())
            );
            String snsSendState = request.getParameter(TokenProp.STATE.getName());

            if (myState.equals("null") | snsSendState == null) {
                log.error("{} 없음.", TokenProp.STATE.getName());
                filterChain.doFilter(request, response);
                return;
            }

            if (snsSendState.equals(myState) && StringUtils.hasText(code)) {
                // state 값이 key 고 value 가 sns 명
                String sns = String.valueOf(session.getAttribute(snsSendState));
                String clientId = snsInfo.getClientId();
                String clientSecret = snsInfo.getClientSecret();
                String tokenUri = snsInfo.getTokenUri();

                removeSessionAttributeState(session, myState);

                HttpEntity<MultiValueMap<String, String>> httpEntity = setHttpEntity
                        (code, requestURI, sns, clientId, clientSecret);
                // https://kauth.kakao.com/oauth/token 으로 토큰 요청 보내기
                ResponseEntity<TokenResponse> responseEntity = sendTokenRequest(tokenUri, httpEntity);


                TokenResponse tokenResponse = responseEntity.getBody();
                request.setAttribute(TokenProp.TOKEN_RESPONSE.name(), tokenResponse);
                request.setAttribute(TokenProp.SNS.getName(), sns);
            }
        }

        filterChain.doFilter(request, response);
    }

    private HttpEntity<MultiValueMap<String, String>> setHttpEntity(String code, String requestURI, String sns, String clientId, String clientSecret) {
        MultiValueMap<String, String> parameters =
                setParameters(code, clientId, clientSecret, requestURI);
        HttpHeaders headers = setHeaders(sns, clientId, clientSecret);
        return new HttpEntity<>(parameters, headers);
    }

    private ResponseEntity<TokenResponse> sendTokenRequest(String tokenUri, HttpEntity<MultiValueMap<String, String>> httpEntity) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(tokenUri, HttpMethod.POST, httpEntity, TokenResponse.class);
    }

    private HttpHeaders setHeaders(String sns, String clientId, String clientSecret) {
        HttpHeaders headers = new HttpHeaders();
        MediaType contentType = valueOf(APPLICATION_FORM_URLENCODED_VALUE);
        headers.setBasicAuth(sns, snsInfo.getClientSecret());
        headers.setContentType(contentType);

        if (sns.equals(Sns.GOOGLE.name())) {
            String authValue =
                    Base64.getEncoder().encodeToString(
                            (clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
            headers.set(HttpHeaders.AUTHORIZATION, "Basic "+authValue);
        }
        return headers;
    }

    private void removeSessionAttributeState(HttpSession session, String originState) {
        session.removeAttribute(TokenProp.STATE.getName());
        session.removeAttribute(originState);
    }

    private MultiValueMap<String, String> setParameters(String code, String clientId, String clientSecret, String requestURI) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        parameters.add(TokenProp.GRANT_TYPE.getName(), TokenProp.AUTHORIZATION_CODE.getName());
        parameters.add(TokenProp.CODE.getName(), code);
        parameters.add(TokenProp.CLIENT_ID.getName(), clientId);
        parameters.add(TokenProp.CLIENT_SECRET.getName(), clientSecret);
        parameters.add(TokenProp.REDIRECT_URI.getName(), requestURI);

        return parameters;
    }
}
