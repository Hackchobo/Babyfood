package com.green.babyfood.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service // 컴퍼먼트여도 상관없다.
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate; // Configuration 에 주소값이 들어온다. @Bean 등록된 부분이 들어온다. DI

    public void setValues(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    } // 메모리 형식이라서 휘발성일 수도있다.

    // 만료시간 설정 -> 자동 삭제
    public void setValuesWithTimeout(String key, String value, long timeout){
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    public String getValues(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

} // CRUD 중에 CSD 만있다.
