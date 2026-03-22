package com.ratelimiter.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/test")
    public String redisTest(){
        redisTemplate.opsForValue().set("intro" , "Hello from redis");
        return redisTemplate.opsForValue().get("intro").toString();
    }
}
