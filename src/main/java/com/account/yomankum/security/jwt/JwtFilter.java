package com.account.yomankum.security.jwt;

import com.account.yomankum.security.oauth.type.TokenProp;
import com.account.yomankum.security.oauth.type.Tokens;
import com.account.yomankum.auth.common.jwt.TokenService;
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

        boolean accessTokenValid = tokenService.tokenValid(authorizationToken);
        if (!accessTokenValid) {
            filterChain.doFilter(request, response);
        }

        String token = tokenService.reCreateToken(authorizationToken);
        String refreshToken = tokenService.createRefreshToken();

        setAuthenticationToSecurityContextHolder(token);

        response.setHeader(HttpHeaders.AUTHORIZATION, TokenProp.BEARER.getKey() + " " + token);
        setCookieInRefreshToken(response, refreshToken);

        filterChain.doFilter(request, response);

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
