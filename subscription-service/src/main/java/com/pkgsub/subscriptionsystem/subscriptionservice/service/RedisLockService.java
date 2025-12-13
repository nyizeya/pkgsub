package com.pkgsub.subscriptionsystem.subscriptionservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisLockService {

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean acquireLock(String key, String value, long ttlMillis) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofMillis(ttlMillis));
        return Boolean.TRUE.equals(success);
    }

    public void releaseLock(String key, String value) {
        Object currentValue = redisTemplate.opsForValue().get(key);
        if (value.equals(currentValue)) {
            redisTemplate.delete(key);
        }
    }
}
