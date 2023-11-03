package com.account.yomankum.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
class SecurityConfig {

    // TODO 이제 UserDetailsService 랑 같이 빈 등록하는 게 필수가 아닌가?
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request.requestMatchers("/").hasRole("USER")
                        .anyRequest().permitAll())

//                .addFilterBefore(new JwtFilter(userDetailsService(), jwtTokenParser, jwtTokenService), UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(new JwtTokenSetFilter(), UsernamePasswordAuthenticationFilter.class)
                // OAuth2
//                .addFilterBefore(new CustomOAuth2AuthorizationCodeGrantFilter(clientRegistrationRepository, oAuth2AuthorizedClientRepository, authenticationManager(authenticationConfiguration()), customDefaultOAuth2UserService, snsInfo), OAuth2LoginAuthenticationFilter.class)
//                .addFilterAfter(new OAuth2JwtTokenFilter(webService, jwtTokenService, jwtTokenParser, memberJoinService, snsInfo, kakaoJwk, googleJwk), OAuth2LoginAuthenticationFilter.class)
//                .exceptionHandling(exception -> exception.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                        .accessDeniedHandler(new CustomAccessDeniedHandler()))

//                .oauth2Login(oauth -> oauth.loginPage("/loginForm")
//                        .authorizationEndpoint(end -> end.authorizationRequestResolver(customOAuth2AuthorizationRequestResolver))
//                        .userInfoEndpoint(userInfo -> userInfo.userService(customDefaultOAuth2UserService))
//                        .successHandler(new CustomOAuth2SuccessHandler(jwtTokenService, memberJoinService)))

                .logout(logout -> logout.logoutSuccessUrl("/login").permitAll())
                .build();
    }
}
