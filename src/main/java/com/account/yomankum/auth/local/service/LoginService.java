package com.account.yomankum.auth.local.service;

import com.account.yomankum.auth.common.jwt.TokenService;
import com.account.yomankum.auth.local.dto.request.LoginRequest;
import com.account.yomankum.auth.local.dto.response.LoginResDto;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.dto.request.FirstLoginUserInfoSaveDto;
import com.account.yomankum.user.service.UserFinder;
import com.account.yomankum.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserFinder userFinder;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public LoginResDto login(LoginRequest loginRequest) {
        User findUser = userFinder.findByEmail(loginRequest.email()).orElseThrow(this::authenticateFailException);

        if (!passwordEncoder.matches(loginRequest.password(), findUser.getPassword())) {
            throw authenticateFailException();
        }

        findUser.updateLastLoginDatetime();

        String accessToken = tokenService.creatToken(findUser.getId(), findUser.getNickname(), findUser.getUserType());
        String refreshToken = tokenService.createRefreshToken();

        return LoginResDto.of(accessToken, refreshToken, findUser);
    }

    public void saveFirstLoginUserInfo(Long userId, FirstLoginUserInfoSaveDto dto) {
        userService.updateUserInfo(userId, dto.toUserInfoUpdateDto());
    }

    // 보안의 이유로 로그인 실패 시 user not found 에러 던지는 것으로 통일
    private BadRequestException authenticateFailException(){
        return new BadRequestException(Exception.USER_NOT_FOUND);
    }

}
