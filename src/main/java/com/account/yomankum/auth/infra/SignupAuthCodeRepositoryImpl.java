package com.account.yomankum.auth.infra;

import com.account.yomankum.auth.repository.SignupAuthCodeRepository;
import lombok.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class SignupAuthCodeRepositoryImpl implements SignupAuthCodeRepository {

    private final StringRedisTemplate redisTemplate;
    private final Duration codeExpireDuration;

    private static String keyPrefix = "signup-auth-code-";

    public SignupAuthCodeRepositoryImpl(@Value("${auth.code.duration.sign-up}")
                                        int codeDuration,
                                        StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        Duration codeExpireDuration = Duration.ofSeconds(codeDuration);
        this.codeExpireDuration = codeExpireDuration;
    }

    @Override
    public void saveCodeByEmail(String mail, String randomCode) {
        String key = getKeyByEmail(mail);
        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))){
            redisTemplate.delete(key);
        }
        redisTemplate.opsForValue().set(key, randomCode, codeExpireDuration);
    }

    @Override
    public String findByEmail(String mail) {
        String key = getKeyByEmail(mail);
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteCodeByEmail(String mail) {
        String key = getKeyByEmail(mail);
        redisTemplate.delete(key);
    }

    private String getKeyByEmail(String mail){
        return keyPrefix + mail;
    }

}
