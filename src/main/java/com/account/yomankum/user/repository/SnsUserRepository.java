package com.account.yomankum.user.repository;

import com.account.yomankum.security.oauth.type.Sns;
import com.account.yomankum.user.domain.SnsUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnsUserRepository extends JpaRepository<SnsUser, Long> {
    Optional<SnsUser> findByUuidKeyAndSns(Sns sns, String uuidKey);
}
