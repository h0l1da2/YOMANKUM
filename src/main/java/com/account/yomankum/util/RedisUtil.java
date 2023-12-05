package com.account.yomankum.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate template;

    // key -> Data get
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        return valueOperations.get(key);
    }

    // key 에 해당하는 value 있는지 확인
    public boolean existData(String key) {
        return Boolean.TRUE.equals(template.hasKey(key));
    }

    // Data save - key , value, 만료 시간 셋팅
    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    // Data delete
    public void deleteData(String key) {
        template.delete(key);
    }
}
