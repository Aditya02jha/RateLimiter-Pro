package com.ratelimiter.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class ScriptConfig {
    @Bean
    public DefaultRedisScript<Long> fixedWindowLuaScript(){
        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("fixed-window.lua"));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    @Bean
    public DefaultRedisScript<Long> slidingWindowLuaScript(){
        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("sliding-window.lua"));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

}
