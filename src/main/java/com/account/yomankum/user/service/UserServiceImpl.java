package com.account.yomankum.user.service;

import com.account.yomankum.common.exception.status4xx.UserNotFoundException;
import com.account.yomankum.security.oauth.type.Tokens;
import com.account.yomankum.security.service.TokenService;
import com.account.yomankum.user.domain.type.RoleName;
import com.account.yomankum.user.domain.Role;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.dto.LoginDto;
import com.account.yomankum.user.dto.UserSignUpDto;
import com.account.yomankum.user.repository.UserRepository;
import com.account.yomankum.web.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserSignUpDto userSignUpDto) throws UserNotFoundException {

        String email = userSignUpDto.getEmail();
        String password = userSignUpDto.getPassword();

        userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(ResponseCode.USER_NOT_FOUND));

        String encodePwd = passwordEncoder.encode(password);

        User user = User.builder()
                .email(email)
                .password(encodePwd)
                .role(new Role(RoleName.ROLE_USER))
                .pwdChangeDate(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    @Override
    public Map<Tokens, String> login(LoginDto loginDto) throws UserNotFoundException {

        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        User findUser = userRepository.findByEmailFetchRole(email)
                .orElseThrow(() -> new UserNotFoundException(ResponseCode.USER_NOT_FOUND));
        String findUserPassword = findUser.getPassword();

        boolean pwdMatches = passwordEncoder.matches(password, findUserPassword);

        if (!pwdMatches) {
            log.error("비밀번호가 안 맞음");
            throw new UserNotFoundException(ResponseCode.USER_NOT_FOUND);
        }

        log.info("아이디 비밀번호 일치 : {}", email);

        String accessToken = tokenService.creatToken(findUser.getId(), findUser.getNickname(), findUser.getRole().getRoleName());
        String refreshToken = tokenService.createRefreshToken();

        Map<Tokens, String> tokenMap = new HashMap<>();
        tokenMap.put(Tokens.ACCESS_TOKEN, accessToken);
        tokenMap.put(Tokens.REFRESH_TOKEN, refreshToken);

        return tokenMap;
    }
}