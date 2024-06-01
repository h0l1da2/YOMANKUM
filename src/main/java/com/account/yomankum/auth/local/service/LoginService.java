package com.account.yomankum.auth.local.service;

import com.account.yomankum.auth.jwt.service.TokenService;
import com.account.yomankum.auth.local.dto.request.LoginRequest;
import com.account.yomankum.auth.local.dto.response.LoginResponse;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.service.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserFinder userFinder;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        User findUser = userFinder.findByEmail(loginRequest.email()).orElseThrow(this::authenticateFailException);

        if (!passwordEncoder.matches(loginRequest.password(), findUser.getPassword())) {
            throw authenticateFailException();
        }

        String accessToken = tokenService.creatToken(findUser.getId(), findUser.getNickname(), findUser.getUserType());
        String refreshToken = tokenService.createRefreshToken(findUser.getId());

        findUser.changeRefreshToken(passwordEncoder.encode(refreshToken));

        return LoginResponse.of(accessToken, refreshToken, findUser);
    }

    private BadRequestException authenticateFailException(){
        return new BadRequestException(Exception.USER_NOT_FOUND);
    }

}
