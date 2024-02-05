package com.account.yomankum.security.jwt;

import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.exception.InternalErrorException;
import com.account.yomankum.security.oauth.type.TokenProp;
import com.account.yomankum.security.oauth.type.Tokens;
import com.account.yomankum.security.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(authorizationToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        tokenVerification(request, response, filterChain, authorizationToken);
        
        String token = tokenService.reCreateToken(authorizationToken);
        String refreshToken = tokenService.createRefreshToken();

        setAuthenticationToSecurityContextHolder(token);

        response.setHeader(HttpHeaders.AUTHORIZATION, TokenProp.BEARER.getName() + " " + token);
        setCookieInRefreshToken(response, refreshToken);

        filterChain.doFilter(request, response);

    }

    private void tokenVerification(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String authorizationToken) {
        boolean accessTokenValid = tokenService.tokenValid(authorizationToken);
        if (!accessTokenValid) {
            Arrays.stream(request.getCookies()).toList().forEach(cookie ->
                        refreshTokenVerification(request, response, filterChain, cookie)
            );
        }
    }

    private void refreshTokenVerification(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Cookie cookie) {
        String cookieName = cookie.getName().toUpperCase();

        if (cookieName.equals(Tokens.REFRESH_TOKEN.name())) {
            String refreshToken = cookie.getValue();
            boolean refreshTokenValid = tokenService.tokenValid(refreshToken);

            if (!refreshTokenValid) {
                try {
                    filterChain.doFilter(request, response);
                } catch (IOException | ServletException e) {
                    log.error("JwtFilter 필터 에러");
                    throw new InternalErrorException(Exception.SERVER_ERROR);
                }
            }
        }
    }

    private void setCookieInRefreshToken(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie(Tokens.REFRESH_TOKEN.name(), refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    private void setAuthenticationToSecurityContextHolder(String token) {
        Long id = tokenService.getIdByToken(token);
        String strId = String.valueOf(id);

        UserDetails userDetails = userDetailsService.loadUserByUsername(strId);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
