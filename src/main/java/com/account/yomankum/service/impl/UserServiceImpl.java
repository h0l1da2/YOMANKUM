package com.account.yomankum.service.impl;

import com.account.yomankum.config.jwt.TokenService;
import com.account.yomankum.domain.Name;
import com.account.yomankum.domain.Role;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserSignUpDto userSignUpDto) throws UserDuplicateException {

        String email = userSignUpDto.getEmail();
        String password = userSignUpDto.getPassword();

        User findUser = userRepository.findByEmail(email)
                .orElse(null);

        if (findUser != null) {
            throw new UserDuplicateException();
        }

        String encodePwd = passwordEncoder.encode(password);
        User user = User.builder()
                .email(email)
                .password(encodePwd)
                .role(new Role(Name.ROLE_USER))
                .pwdChangeDate(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    @Override
    public Map<String, String> login(LoginDto loginDto) throws IncorrectLoginException {

        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        User findUser = userRepository.findByEmailFetchRole(email)
                .orElseThrow(IncorrectLoginException::new);

        boolean pwdMatches = passwordEncoder.matches(password, findUser.getPassword());

        if (!pwdMatches) {
            throw new IncorrectLoginException();
        }

        // 맞다면, JWT 발급
        String accessToken = tokenService.creatToken(findUser.getId(), findUser.getNickname(), findUser.getRole().getName());
        String refreshToken = tokenService.createRefreshToken();

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        return tokenMap;
    }
}
