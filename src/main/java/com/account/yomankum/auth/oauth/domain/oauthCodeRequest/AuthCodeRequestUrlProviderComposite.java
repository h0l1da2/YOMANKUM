package com.account.yomankum.auth.oauth.domain.oauthCodeRequest;

import com.account.yomankum.user.domain.AuthType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;


@Component
public class AuthCodeRequestUrlProviderComposite {

    private final Map<AuthType, AuthCodeRequestUrlProvider> providersByType;

    public AuthCodeRequestUrlProviderComposite(Set<AuthCodeRequestUrlProvider> providers){
        providersByType = providers.stream().collect(toMap(AuthCodeRequestUrlProvider::support, Function.identity()));
    }

    public String getOauthCodeRequestUrl(AuthType type){
        return providersByType.get(type).getUrl();
    }
}
