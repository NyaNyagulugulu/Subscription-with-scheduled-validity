package com.neko.Sub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String TOKEN_PREFIX = "token:";

    public boolean isTokenValid(String token, int invalidTimeMinutes) {
        String key = TOKEN_PREFIX + token;
        String lastUsedTime = redisTemplate.opsForValue().get(key);

        if (lastUsedTime == null) {
            return true;
        }

        long lastUsed = Long.parseLong(lastUsedTime);
        long currentTime = System.currentTimeMillis();
        long diffMinutes = (currentTime - lastUsed) / (60 * 1000);

        return diffMinutes < invalidTimeMinutes;
    }

    public void updateToken(String token) {
        String key = TOKEN_PREFIX + token;
        redisTemplate.opsForValue().set(key, String.valueOf(System.currentTimeMillis()), 7, TimeUnit.DAYS);
    }
}