package com.account.yomankum.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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

            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {

                String cookieName = cookie.getName();

                if (cookieName.equals("refreshToken")) {

                    String refreshToken = cookie.getValue();
                    boolean refreshTokenValid = tokenService.tokenValid(refreshToken);

                    if (!refreshTokenValid) {
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
            }
        }



        String token = tokenService.reCreateToken(authorizationToken);
        String refreshToken = tokenService.createRefreshToken();


        setAuthenticationToSecurityContextHolder(token);


        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        filterChain.doFilter(request, response);

    }

    private void setAuthenticationToSecurityContextHolder(String token) {
        Long id = tokenService.getIdByToken(token);
        String strId = String.valueOf(id);

        UserDetails userDetails = userDetailsService.loadUserByUsername(strId);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
