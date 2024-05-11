package com.account.yomankum.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Embeddable
@NoArgsConstructor
public class AuthInfo {

    private String refreshToken;
    @Enumerated(EnumType.STRING)
    private AuthType authType;
    private String oauthId;

    protected void setRefreshToken(String token){
        refreshToken = token;
    }

    public AuthInfo(AuthType type, String oauthId){
        this.authType = type;
        this.oauthId = oauthId;
    }

    public static AuthInfo localUser() {
        AuthInfo authInfo = new AuthInfo();
        authInfo.authType = AuthType.LOCAL;
        return authInfo;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }
}
