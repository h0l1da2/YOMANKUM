package com.account.yomankum.security.service;

import com.account.yomankum.security.oauth.type.Sns;
import com.account.yomankum.user.domain.Role;
import com.account.yomankum.user.domain.SnsUser;
import com.account.yomankum.user.domain.type.RoleName;
import com.account.yomankum.common.exception.status4xx.UserNotFoundException;
import com.account.yomankum.user.repository.SnsUserRepository;
import com.account.yomankum.web.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class SnsUserServiceImpl implements SnsUserService {

    private final SnsUserRepository snsUserRepository;
    @Override
    public SnsUser login(Sns sns, String uuidKey) throws UserNotFoundException {
        return snsUserRepository.findByUuidKeyAndSns(sns, uuidKey).orElseThrow(() -> new UserNotFoundException(ResponseCode.USER_NOT_FOUND));
    }

    @Override
    public SnsUser signUp(Sns sns, String email, String uuidKey) {
        SnsUser snsUser = SnsUser.builder()
                .uuidKey(uuidKey)
                .joinDate(now())
                .email(email)
                .pwdChangeDate(now())
                .sns(sns)
                .role(new Role(RoleName.ROLE_USER))
                .build();

        return snsUserRepository.save(snsUser);    }
}
