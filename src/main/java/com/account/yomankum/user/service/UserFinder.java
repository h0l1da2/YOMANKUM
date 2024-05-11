package com.account.yomankum.user.service;

import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.user.domain.AuthType;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.dto.response.UserInfoDto;
import com.account.yomankum.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFinder {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long userId){
        return userRepository.findById(userId);
    }

    public UserInfoDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));
        return UserInfoDto.from(user);
    }

    public Optional<User> findByAuthTypeAndOauthId(AuthType type, String oauthId){
        return userRepository.findByAuthInfoAuthTypeAndAuthInfoOauthId(type, oauthId);
    }

}
