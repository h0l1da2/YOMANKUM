package com.account.yomankum.auth.jwt.service;

import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.service.UserFinder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final TokenService tokenService;
    private final UserFinder userFinder;
    private final PasswordEncoder passwordEncoder;

    public String reissueToken(HttpServletRequest request){
        String token = tokenService.resolveToken(request);
        Long userId = tokenService.getUserId(token);
        Optional<User> userOptional = userFinder.findById(userId);
        if(userOptional.isEmpty() || !passwordEncoder.matches(token, userOptional.get().getRefreshToken())){
            throw new BadRequestException(Exception.TOKEN_NOT_VALID);
        }

        User user = userOptional.get();
        return tokenService.creatToken(user.getId(), user.getNickname(), user.getUserType());
    }
}
