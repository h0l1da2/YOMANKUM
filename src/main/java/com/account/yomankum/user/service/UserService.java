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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(User user) {
        user.encryptPassword(passwordEncoder);
        userRepository.save(user);
    }

    public void updateUserInfo(Long userId, UserInfoUpdateRequest request) {
        User findUser = findById(userId);
        findUser.updateUserInfo(request);
    }

    public void updatePasswordByUserId(Long userId, String password){
        User findUser = findById(userId);
        findUser.changePassword(passwordEncoder.encode(password));
    }

    public void updatePasswordByEmail(String email, String password){
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(()->new BadRequestException(Exception.USER_NOT_FOUND));
        findUser.changePassword(passwordEncoder.encode(password));
    }

    private User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new BadRequestException(Exception.USER_NOT_FOUND));
    }
}
