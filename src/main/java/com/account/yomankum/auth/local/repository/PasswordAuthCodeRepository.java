package com.account.yomankum.auth.local.repository;

import java.util.Optional;

public interface PasswordAuthCodeRepository {
    void saveCodeByEmail(String mail, String randomCode);
    Optional<String> findByEmail(String mail);
    void deleteCodeByEmail(String mail);
}
