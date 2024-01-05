package com.account.yomankum.security.oauth;

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

    public CustomOAuth2AuthorizationCodeGrantFilter(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository authorizedClientRepository, AuthenticationManager authenticationManager, SnsInfo snsInfo) {
        super(clientRegistrationRepository, authorizedClientRepository, authenticationManager);
        this.snsInfo = snsInfo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("그랜트 필터 시작");

        // https://localhost:8080?code={code}&state={state}
        String code = request.getParameter("code");

        if (!StringUtils.hasText(code)) {
            log.error("코드 없음");
            filterChain.doFilter(request, response);
            return;
        }

        String requestURI = request.getRequestURL().toString();
        HttpSession session = request.getSession();

        if (session != null) {
            String myState = String.valueOf(session.getAttribute("state"));
            String snsState = request.getParameter("state");

            if (myState == null | snsState == null) {
                log.error("state 없음.");
                filterChain.doFilter(request, response);
                return;
            }

            if (snsState.equals(myState) && StringUtils.hasText(code)) {
                /**
                 * 카카오 -
                 * state가 동일하고, 정상 응답이 왔을 경우(코드 받았음)
                 */
                // state 값이 key 고 value 가 sns 명
                String sns = String.valueOf(session.getAttribute(snsState));
                String clientId = snsInfo.clientId(sns);
                String clientSecret = snsInfo.clientSecret(sns);
                String tokenUri = snsInfo.tokenUri(sns);

                // 세션 부하를 위해 일단 세션 데이터부터 삭제
                removeSessionAttributeState(session, myState);


                MultiValueMap<String, String> parameters =
                        setParameters(code, clientId, clientSecret, requestURI);
                HttpHeaders headers = setHeaders(sns, clientId, clientSecret);
                HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);

                // https://kauth.kakao.com/oauth/token 으로 토큰 요청 보내기
                ResponseEntity<TokenResponse> responseEntity = sendTokenRequest(tokenUri, httpEntity);


                // (토큰) 응답 받기
                // 카카오, 네이버, 구글이 다 다름 TokenResponse 가
                TokenResponse tokenResponse = responseEntity.getBody();
                request.setAttribute("tokenResponse", tokenResponse);
                request.setAttribute("sns", sns);

            }
        }



        filterChain.doFilter(request, response);


    }

    private ResponseEntity<TokenResponse> sendTokenRequest(String tokenUri, HttpEntity<MultiValueMap<String, String>> httpEntity) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TokenResponse> responseEntity =
                restTemplate.exchange(tokenUri, HttpMethod.POST, httpEntity, TokenResponse.class);
        return responseEntity;
    }

    private HttpHeaders setHeaders(String sns, String clientId, String clientSecret) {
        HttpHeaders headers = new HttpHeaders();
        MediaType contentType = valueOf(APPLICATION_FORM_URLENCODED_VALUE);
        headers.setBasicAuth(sns, snsInfo.clientSecret(sns));
        headers.setContentType(contentType);

        if (sns.equals(Sns.GOOGLE.name())) {

            String authValue =
                    Base64.getEncoder().encodeToString(
                            (clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
            headers.set("Authorization", "Basic "+authValue);

        }
        return headers;
    }

    private void removeSessionAttributeState(HttpSession session, String originState) {
        session.removeAttribute("state");
        session.removeAttribute(originState);
    }

    private MultiValueMap<String, String> setParameters(String code, String clientId, String clientSecret, String requestURI) {

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        String redirectUri = requestURI;

        parameters.add("grant_type", "authorization_code");
        parameters.add("code", code);
        parameters.add("client_id", clientId);
        parameters.add("client_secret", clientSecret);
        parameters.add("redirect_uri", redirectUri);

        return parameters;
    }
}
