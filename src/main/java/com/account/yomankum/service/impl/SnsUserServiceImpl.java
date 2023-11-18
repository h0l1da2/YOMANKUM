package com.account.yomankum.service.impl;

import com.account.yomankum.config.oauth.Sns;
import com.account.yomankum.domain.Name;
import com.account.yomankum.domain.Role;
import com.account.yomankum.domain.SnsUser;
import com.account.yomankum.repository.SnsUserRepository;
import com.account.yomankum.service.SnsUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.*;

@Service
@RequiredArgsConstructor
public class SnsUserServiceImpl implements SnsUserService {

    private final SnsUserRepository snsUserRepository;

    @Override
    public SnsUser loginCheck(Sns sns, String email, String uuidKey) {
        return snsUserRepository.findByEmailAndUAndUuidKeyAndSns(sns, email, uuidKey).orElse(null);
    }

    @Override
    public SnsUser signUp(Sns sns, String email, String uuidKey) {
        SnsUser snsUser = SnsUser.builder()
                .uuidKey(uuidKey)
                .email(email)
                .joinDate(now())
                .pwdChangeDate(now())
                .sns(sns)
                .role(new Role(Name.ROLE_USER))
                .build();

        return snsUserRepository.save(snsUser);
    }
}
