package com.account.yomankum.auth.oauth.service;

import com.account.yomankum.auth.jwt.service.TokenService;
import com.account.yomankum.auth.local.dto.response.LoginResponse;
import com.account.yomankum.auth.oauth.domain.memberClient.OauthUserClientComposite;
import com.account.yomankum.auth.oauth.domain.oauthCodeRequest.AuthCodeRequestUrlProviderComposite;
import com.account.yomankum.auth.oauth.dto.OauthSignupRequest;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.user.domain.AuthType;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.service.UserFinder;
import com.account.yomankum.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthUserClientComposite oauthUserClientComposite;
    private final TokenService tokenService;
    private final UserFinder userFinder;
    private final UserService userService;

    public String getOauthCodeRequestPageUrl(AuthType type){
        return authCodeRequestUrlProviderComposite.getOauthCodeRequestUrl(type);
    }

    public LoginResponse login(AuthType type, String code) {
        User user = getUser(type, code);
        String token = tokenService.creatToken(user.getId(), user.getNickname(), user.getUserType());
        String refreshToken = tokenService.createRefreshToken(user.getId());
        user.changeRefreshToken(refreshToken);
        return new LoginResponse(token, refreshToken, user.getId(), user.getPicture());
    }

    public Long signup(OauthSignupRequest request){
        userFinder.findByEmail(request.email())
                .ifPresent(user -> {throw new BadRequestException(Exception.DUPLICATED_USER);});
        User user = request.toUser();
        userService.create(user);
        return user.getId();
    }

    private User getUser(AuthType type, String code) {
        User user = oauthUserClientComposite.findOauthUser(type, code);
        return userFinder.findByAuthTypeAndOauthId(type, user.getOauthId())
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));
    }
}

