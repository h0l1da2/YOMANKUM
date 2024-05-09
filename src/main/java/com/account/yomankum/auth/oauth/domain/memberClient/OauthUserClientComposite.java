package com.account.yomankum.auth.oauth.domain.memberClient;

import com.account.yomankum.user.domain.AuthType;
import com.account.yomankum.user.domain.User;
import com.develog.domain.oauth.OauthMember;
import com.develog.domain.oauth.OauthType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OauthUserClientComposite {

    private final Map<AuthType, OauthUserClient> clientsByType;

    public OauthUserClientComposite(List<OauthUserClient> clients){
        clientsByType = clients.stream().collect(Collectors.toMap(OauthUserClient::support, Function.identity()));
    }

    public User findOauthUser(AuthType type, String code){
        return clientsByType.get(type).findUser(code);
    }
}
