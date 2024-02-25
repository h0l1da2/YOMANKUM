package com.account.yomankum.security.service;

import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.security.oauth.type.Sns;
import com.account.yomankum.user.domain.Role;
import com.account.yomankum.user.domain.SnsUser;
import com.account.yomankum.user.domain.type.RoleName;
import com.account.yomankum.user.repository.SnsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SnsUserServiceImpl implements SnsUserService {

    private final SnsUserRepository snsUserRepository;
    @Override
    public SnsUser login(Sns sns, String uuidKey) {
        return snsUserRepository.findByUuidKeyAndSns(sns, uuidKey)
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));
    }

    @Override
    public SnsUser signUp(Sns sns, String email, String uuidKey) {
        SnsUser snsUser = SnsUser.builder()
                .uuidKey(uuidKey)
                .joinDatetime(Instant.now())
                .email(email)
                .pwdChangeDatetime(Instant.now())
                .sns(sns)
                .role(new Role(RoleName.ROLE_USER))
                .build();

        return snsUserRepository.save(snsUser);
    }
}
