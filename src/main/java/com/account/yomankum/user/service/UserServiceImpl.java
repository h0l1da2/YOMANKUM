package com.account.yomankum.user.service;

import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.exception.InternalErrorException;
import com.account.yomankum.security.oauth.type.TokenProp;
import com.account.yomankum.security.oauth.type.Tokens;
import com.account.yomankum.security.service.CustomUserDetails;
import com.account.yomankum.security.service.TokenService;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.dto.UserDto.UserSignUpDto;
import com.account.yomankum.user.dto.request.FirstLoginUserInfoSaveDto;
import com.account.yomankum.user.dto.request.UserInfoUpdateDto;
import com.account.yomankum.user.dto.response.UserInfoDto;
import com.account.yomankum.user.repository.UserQueryRepository;
import com.account.yomankum.user.repository.UserRepository;
import com.account.yomankum.util.RedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
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
    private final RedisUtil redisUtil;
    private final UserQueryRepository userQueryRepository;

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

    @Override
    public UserInfoDto getUserInfo(String bearerJwt) {
        // Bearer dsadj2e ...
        String jwt = bearerJwt.substring(TokenProp.BEARER.name().length() + 1);
        Long userId = tokenService.getIdByToken(jwt);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));

        return UserInfoDto.from(user);
    }

    @Override
    public void updatePassword(String uuid, String passwordJson) {
        String userEmail = redisUtil.getData(uuid);
        if (!StringUtils.hasText(userEmail)) {
            throw new BadRequestException(Exception.USER_NOT_FOUND);
        }
        redisUtil.deleteData(uuid);

        String password = "";
        try {
            password = new ObjectMapper().readTree(passwordJson)
                    .path("password").asText();
        } catch (JsonProcessingException e) {
            throw new BadRequestException(Exception.REQUEST_NOT_FOUND);
        }

        long execute = userQueryRepository.updateUserPassword(userEmail, passwordEncoder.encode(password));

        if (execute != 1) {
            log.error("모종의 이유로 패스워드 업데이트 오류. (확인 필요)");
            throw new InternalErrorException(Exception.SERVER_ERROR);
        }

    }

    @Override
    public void saveFirstLoginUserInfo(FirstLoginUserInfoSaveDto firstLoginUserInfoSaveDto, Principal principal) {
        CustomUserDetails userDetails = (CustomUserDetails) principal;

        User findUser = userRepository.findByEmailFetchRole(userDetails.getUsername())
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));

        findUser.updateFirstUserInfo(firstLoginUserInfoSaveDto);
        userRepository.save(findUser);
    }

    @Override
    public void updateUserInfo(CustomUserDetails userDetails, UserInfoUpdateDto dto) {
        User findUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));

        findUser.updateUserInfo(dto);
        userRepository.save(findUser);
    }
}
