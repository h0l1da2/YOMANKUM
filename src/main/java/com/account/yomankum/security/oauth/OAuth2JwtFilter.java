package com.account.yomankum.security.oauth;

import com.account.yomankum.security.CustomUserDetails;
import com.account.yomankum.security.jwt.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final CustomDefaultOAuth2UserService customDefaultOAuth2UserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        TokenResponse tokenResponse =
                (TokenResponse) request.getAttribute("tokenResponse");
        String sns = String.valueOf(request.getAttribute("sns"));

        String memberId = "";
        String token = "";
        String snsUUID = "";

        if (tokenResponse != null && StringUtils.hasText(sns)) {
            /**
             * - 토큰 파싱을 위해서는 발급자(--sns) 가 필요
             * iss : sns (발급자)
             * sub : 식별자
             * KAKAO : nickname , email
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
                snsUUID = profileResponse.getResponse().getId();

            }

            // KAKAO 일 경우 작업
            if (sns.equals(Sns.KAKAO.name()) | sns.equals(Sns.GOOGLE.name())) {
                token = tokenResponse.getIdToken();
                snsUUID = tokenService.getSnsUUID(sns, token);

            }

        }



            // authentication 생성 후, SpringContext에 저장하는 작업
            ClientRegistration clientRegistration =
                    clientRegistrationRepository.findByRegistrationId(sns.toLowerCase());
            OAuth2AccessToken oAuth2AccessToken = getOAuth2AccessToken(tokenResponse);

            OAuth2UserRequest oAuth2UserRequest = new OAuth2UserRequest(clientRegistration, oAuth2AccessToken);

            OAuth2User oAuth2User = customDefaultOAuth2UserService.loadUser(oAuth2UserRequest);
            CustomUserDetails cu = (CustomUserDetails) oAuth2User;

//            // 토큰 만들기
//            Member member = memberJoinService.findMyAccount(cu.getId());
//            String accessToken = "";
//            if (member != null) {
//                Map<TokenName, String> tokens =
//                        jwtTokenService.getTokens(member.getId(), member.getRole().getName());
//                accessToken = tokens.get(TokenName.ACCESS_TOKEN);
//                String refreshToken = tokens.get(TokenName.REFRESH_TOKEN);
//                request.setAttribute(TokenName.ACCESS_TOKEN.name(), accessToken);
//                jwtTokenService.saveRefreshToken(member.getId(), refreshToken);
//
//            }
//
//            setAuthenticationSpringContext(oAuth2User, accessToken);
//
//            String redirectUri = "/loginForm?redirect="+request.getRequestURI()+"&token="+accessToken;
//            response.sendRedirect(redirectUri);
//
//            webService.sessionSetMember(member, request);
//
//        }
//        filterChain.doFilter(request, response);

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
