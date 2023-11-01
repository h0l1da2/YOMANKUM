package com.account.yomankum.service.impl;

import com.account.yomankum.domain.User;
import com.account.yomankum.domain.dto.UserSignUpDto;
import com.account.yomankum.exception.UserDuplicateException;
import com.account.yomankum.repository.UserRepository;
import com.account.yomankum.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserSignUpDto userSignUpDto) throws UserDuplicateException {

        String username = userSignUpDto.getUsername();
        String password = userSignUpDto.getPassword();

        User findUser = userRepository.findByUsername(username)
                .orElse(null);

        if (findUser != null) {
            throw new UserDuplicateException();
        }

        String encodePwd = passwordEncoder.encode(password);

        User user = User.builder()
                .username(username)
                .password(encodePwd)
                .build();

        userRepository.save(user);
    }
}
