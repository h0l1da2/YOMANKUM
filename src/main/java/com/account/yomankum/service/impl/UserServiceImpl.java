package com.account.yomankum.service.impl;

import com.account.yomankum.domain.User;
import com.account.yomankum.domain.dto.LoginDto;
import com.account.yomankum.domain.dto.UserSignUpDto;
import com.account.yomankum.exception.IncorrectLoginException;
import com.account.yomankum.exception.UserDuplicateException;
import com.account.yomankum.repository.UserRepository;
import com.account.yomankum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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

    @Override
    public void login(LoginDto loginDto) throws IncorrectLoginException {

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        User findUser = userRepository.findByUsername(username)
                .orElseThrow(IncorrectLoginException::new);

        boolean pwdMatches = passwordEncoder.matches(password, findUser.getPassword());

        if (!pwdMatches) {
            throw new IncorrectLoginException();
        }

        // 맞다면, JWT 발급


    }
}
