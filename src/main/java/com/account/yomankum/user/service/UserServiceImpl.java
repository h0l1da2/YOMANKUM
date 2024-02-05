package com.account.yomankum.user.service;

import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.security.oauth.type.Tokens;
import com.account.yomankum.security.service.TokenService;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.dto.UserDto.UserSignUpDto;
import com.account.yomankum.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.account.yomankum.user.dto.UserDto.UserLoginDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserSignUpDto userSignUpDto) {

        String email = userSignUpDto.email();
        User findUser = userRepository.findByEmail(email)
                .orElse(null);

        if (findUser != null) {
            throw new BadRequestException(Exception.DUPLICATED_USER);
        }

        String password = userSignUpDto.password();
        User user = userSignUpDto.toEntity(
                passwordEncoder.encode(password)
        );

        userRepository.save(user);
    }

    @Override
    public Map<Tokens, String> login(UserLoginDto userLoginDto) {

        String email = userLoginDto.email();
        String password = userLoginDto.password();

        User findUser = userRepository.findByEmailFetchRole(email)
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));
        String findUserPassword = findUser.getPassword();

        boolean pwdMatches = passwordEncoder.matches(password, findUserPassword);

        if (!pwdMatches) {
            log.error("비밀번호가 안 맞음");
            throw new BadRequestException(Exception.USER_NOT_FOUND);
        }

        String accessToken = tokenService.creatToken(findUser.getId(), findUser.getNickname(), findUser.getRole().getRoleName());
        String refreshToken = tokenService.createRefreshToken();

        Map<Tokens, String> tokenMap = new HashMap<>();
        tokenMap.put(Tokens.ACCESS_TOKEN, accessToken);
        tokenMap.put(Tokens.REFRESH_TOKEN, refreshToken);

        return tokenMap;
    }
}
