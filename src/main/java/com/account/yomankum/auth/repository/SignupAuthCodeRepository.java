package com.account.yomankum.auth.repository;

public interface SignupAuthCodeRepository {
    void saveCodeByEmail(String mail, String randomCode);
    String findByEmail(String mail);
    void deleteCodeByEmail(String mail);
}
