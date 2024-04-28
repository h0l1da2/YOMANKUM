package com.account.yomankum.auth.service;

import com.account.yomankum.auth.dto.response.LoginResDto;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.dto.UserDto;
import com.account.yomankum.user.dto.request.FirstLoginUserInfoSaveDto;
import com.account.yomankum.user.service.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserFinder userFinder;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResDto login(UserDto.UserLoginDto userLoginDto) {
        String email = userLoginDto.email();
        String password = userLoginDto.password();

        User findUser = userFinder.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));
        String findUserPassword = findUser.getPassword();

        boolean pwdMatches = passwordEncoder.matches(password, findUserPassword);

        if (!pwdMatches) {
            log.error("비밀번호가 안 맞음");
            throw new BadRequestException(Exception.USER_NOT_FOUND);
        }

        findUser.updateLastLoginDatetime();

        String accessToken = tokenService.creatToken(findUser.getId(), findUser.getNickname(), findUser.getUserType());
        String refreshToken = tokenService.createRefreshToken();

        return LoginResDto.of(accessToken, refreshToken, findUser);
    }

    public void saveFirstLoginUserInfo(Long userId, FirstLoginUserInfoSaveDto dto) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));

        findUser.updateFirstUserInfo(dto);
        userRepository.save(findUser);
    }

}
