package com.account.yomankum.repository;

import com.account.yomankum.login.security.oauth.Sns;
import com.account.yomankum.domain.SnsUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnsUserRepository extends JpaRepository<SnsUser, Long> {
    Optional<SnsUser> findByEmailAndUuidKeyAndSns(Sns sns, String email, String uuidKey);
}
