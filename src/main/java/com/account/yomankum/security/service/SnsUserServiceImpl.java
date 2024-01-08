package com.account.yomankum.security.service;

import com.account.yomankum.domain.Role;
import com.account.yomankum.domain.SnsUser;
import com.account.yomankum.domain.enums.Name;
import com.account.yomankum.repository.SnsUserRepository;
import com.account.yomankum.security.oauth.Sns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class SnsUserServiceImpl implements SnsUserService {

    private final SnsUserRepository snsUserRepository;
    @Override
    public SnsUser login(Sns sns, String uuidKey) {
        return snsUserRepository.findByUuidKeyAndSns(sns, uuidKey).orElse(null);
    }

    @Override
    public SnsUser signUp(Sns sns, String email, String uuidKey) {
        SnsUser snsUser = SnsUser.builder()
                .uuidKey(uuidKey)
                .joinDate(now())
                .email(email)
                .pwdChangeDate(now())
                .sns(sns)
                .role(new Role(Name.ROLE_USER))
                .build();

        return snsUserRepository.save(snsUser);    }
}
