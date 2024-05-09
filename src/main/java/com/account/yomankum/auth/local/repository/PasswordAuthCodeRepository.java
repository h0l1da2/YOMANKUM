package com.account.yomankum.auth.local.repository;

import java.util.Optional;

public interface PasswordAuthCodeRepository {
    void saveCodeByEmail(String mail, String randomCode);
    String findByEmail(String mail);
    void deleteCodeByEmail(String mail);
}
