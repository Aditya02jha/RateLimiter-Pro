package com.ratelimiter.api.controller;

import com.ratelimiter.redis.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    RateLimiterService rateLimiterService;

    @GetMapping("/test")
    public String redisTest(){
        String key = "hello";
        rateLimiterService.incrementCounterExpireIfNew(key, 60);
        long count = rateLimiterService.getCounter(key);
        String returnString = "Counter for key '" + key + "' is: " + count;
        System.out.println(returnString);
        rateLimiterService.incrementCounterExpireIfNew(key, 60);
        rateLimiterService.getCounter(key);
        returnString += " after incrementing again: " + rateLimiterService.getCounter(key);
        System.out.println(returnString);
        return returnString;
    }
}
