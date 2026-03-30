package com.ratelimiter.redis.service;

import com.ratelimiter.redis.config.ScriptConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import response.RateLimitResponse;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    ScriptConfig scriptConfig;

    @Override
    public void incrementCounterExpireIfNew(String key , long expireTimeInSeconds) {
        Boolean isNewKey = stringRedisTemplate.opsForValue().setIfAbsent(key, "0", expireTimeInSeconds, java.util.concurrent.TimeUnit.SECONDS);
        if (Boolean.FALSE.equals(isNewKey)) {
            stringRedisTemplate.opsForValue().increment(key);
        }
    }

    @Override
    public long getCounter(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        return value != null ? Long.parseLong(value) : 0;
    }

    @Override
    public RateLimitResponse incrementAndGet(String key, int windowSeconds, int limit) {
        Long Result  = stringRedisTemplate.execute(
                scriptConfig.fixedWindowLuaScript(),
                java.util.Collections.singletonList(key),
                String.valueOf(limit),
                String.valueOf(windowSeconds)
        );
        if(Result != 0){
            return new RateLimitResponse(true , limit - Result.intValue());
        }
        return new RateLimitResponse(false, 0);
    }
    @Override
    public RateLimitResponse evaluateSlidingWindow(String key, int windowSeconds, int limit) {
        Long Result  = stringRedisTemplate.execute(
                scriptConfig.slidingWindowLuaScript(),
                java.util.Collections.singletonList(key),
                String.valueOf(limit),
                String.valueOf(windowSeconds)
        );
        if(Result != 0){
            return new RateLimitResponse(true , limit - Result.intValue());
        }
        return new RateLimitResponse(false, 0);
    }

}
