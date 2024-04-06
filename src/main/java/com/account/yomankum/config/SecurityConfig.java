package com.account.yomankum.config;

import com.account.yomankum.security.jwt.JwtFilter;
import com.account.yomankum.security.oauth.filter.CustomOAuth2AuthorizationRequestResolver;
import com.account.yomankum.security.oauth.filter.OAuth2JwtFilter;
import com.account.yomankum.security.oauth.handler.CustomAccessDeniedHandler;
import com.account.yomankum.security.oauth.handler.CustomAuthenticationEntryPoint;
import com.account.yomankum.security.oauth.service.CustomDefaultOAuth2UserService;
import com.account.yomankum.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
class SecurityConfig {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationConfiguration authenticationConfiguration() {
        return new AuthenticationConfiguration();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtFilter jwtFilter = new JwtFilter(tokenService, userDetailsService);
        return http
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/").hasRole("USER")
                        .anyRequest().permitAll())

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutSuccessUrl("/api/v1/login").permitAll())

                // OAuth2
//                .addFilterBefore(new CustomOAuth2AuthorizationCodeGrantFilter(
//                        clientRegistrationRepository,
//                        oAuth2AuthorizedClientRepository,
//                        authenticationManager,
//                        snsInfo), OAuth2LoginAuthenticationFilter.class)
//                .addFilterAfter(oAuth2JwtFilter, OAuth2LoginAuthenticationFilter.class)
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(authenticationEntryPoint)
//                        .accessDeniedHandler(accessDeniedHandler)
//                )
//                .oauth2Login(oauth -> oauth
//                        .authorizationEndpoint(end -> end.authorizationRequestResolver(authorizationRequestResolver))
//                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
//                )
                .build();
    }
}
