package com.account.yomankum.security.oauth;

import com.account.yomankum.domain.SnsUser;
import com.account.yomankum.security.domain.NaverProfileApiResponse;
import com.account.yomankum.security.domain.Sns;
import com.account.yomankum.security.domain.SnsInfo;
import com.account.yomankum.security.domain.TokenResponse;
import com.account.yomankum.security.service.SnsUserService;
import com.account.yomankum.security.jwt.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
public class OAuth2JwtFilter extends OncePerRequestFilter {

    private final SnsInfo snsInfo;
    private final TokenService tokenService;
    private final SnsUserService snsUserService;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final CustomDefaultOAuth2UserService customDefaultOAuth2UserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        TokenResponse tokenResponse =
                (TokenResponse) request.getAttribute("tokenResponse");
        String sns = String.valueOf(request.getAttribute("sns"));

        Sns snsEnum = null;
        String token = "";
        String snsUuidKey = "";

        if (tokenResponse != null && StringUtils.hasText(sns)) {
            /**
             * - 토큰 파싱을 위해서는 발급자(--sns) 가 필요
             * iss : sns (발급자)
             * sub : 식별자
             * KAKAO : nickname , email(필요한데 서비스 오픈해야 받을 수 있음..)
             * NAVER : email
             * GOOGLE : email , name
             */

            // 네이버는 프로필 정보를 요청해야 합니다.
            if (sns.equals(Sns.NAVER.name())) {
                // 헤더 세팅
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer "+tokenResponse.getAccessToken());
                HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

                // https://openapi.naver.com/v1/nid/me 으로 프로필 정보 요청 보내기

                RestTemplate restTemplate = new RestTemplate();

                String naverProfileApiUri = snsInfo.getNaverProfileApiUri();
                ResponseEntity<NaverProfileApiResponse> responseEntity =
                        restTemplate.exchange(naverProfileApiUri, HttpMethod.GET,
                                httpEntity, NaverProfileApiResponse.class);

                NaverProfileApiResponse profileResponse = responseEntity.getBody();
                snsUuidKey = profileResponse.getResponse().getId();
                snsEnum = Sns.NAVER;

            }
            // KAKAO 일 경우 작업
            else if (sns.equals(Sns.KAKAO.name())) {
                token = tokenResponse.getIdToken();
                snsUuidKey = tokenService.getSnsUUID(sns, token);
                // 카카오는 서비스 오픈 안 하면 이메일은 가져올 수 없음
                snsEnum = Sns.KAKAO;

            }

        }

        // authentication 생성 후, SpringContext에 저장하는 작업
        ClientRegistration clientRegistration =
                clientRegistrationRepository.findByRegistrationId(sns.toLowerCase());
        OAuth2AccessToken oAuth2AccessToken = getOAuth2AccessToken(tokenResponse);

        OAuth2UserRequest oAuth2UserRequest = new OAuth2UserRequest(clientRegistration, oAuth2AccessToken);

        OAuth2User oAuth2User = customDefaultOAuth2UserService.loadUser(oAuth2UserRequest);

        // 토큰 만들기
        SnsUser snsUser = snsUserService.login(snsEnum, snsUuidKey);
        String accessToken = tokenService.creatToken(snsUser.getId(), snsUser.getNickname(), snsUser.getRole().getName());
        String refreshToken = tokenService.createRefreshToken();

        setAuthenticationSpringContext(oAuth2User, accessToken);

        setTokensAtReponse(response, accessToken, refreshToken);
        setIdAndNicknameAtSession(request, snsUser.getId(), snsUser.getNickname());

        response.sendRedirect("/");

        filterChain.doFilter(request, response);
    }

    private void setTokensAtReponse(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        response.addCookie(new Cookie("refreshToken", refreshToken));
    }

    private void setIdAndNicknameAtSession(HttpServletRequest request, Long id, String nickname) {
        request.getSession().setAttribute("id", id);
        request.getSession().setAttribute("nickname", nickname);
    }

    private void setAuthenticationSpringContext(OAuth2User oAuth2User, String accessToken) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(oAuth2User, accessToken, oAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private OAuth2AccessToken getOAuth2AccessToken(TokenResponse tokenResponse) {
        String accessToken = tokenResponse.getAccessToken();
        long expiresIn = tokenResponse.getExpiresIn();
        Instant expiresAt = Instant.now().plusSeconds(expiresIn);
        return new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, accessToken, Instant.now(), expiresAt);
    }
}
