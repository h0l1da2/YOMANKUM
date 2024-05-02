package com.account.yomankum.user.service;

import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.dto.request.UserInfoUpdateRequest;
import com.account.yomankum.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(User user) {
        userRepository.save(user);
    }

    public void updateUserInfo(Long userId, UserInfoUpdateRequest request) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));
        findUser.updateUserInfo(request);
    }

    public void updatePassword(Long userId, String password){
        User findUser = userRepository.findById(userId)
                .orElseThrow(()->new BadRequestException(Exception.USER_NOT_FOUND));
        findUser.updatePassword(passwordEncoder, password);
    }

    public void updatePassword(String email, String password){
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(()->new BadRequestException(Exception.USER_NOT_FOUND));
        findUser.updatePassword(passwordEncoder, password);
    }
}
