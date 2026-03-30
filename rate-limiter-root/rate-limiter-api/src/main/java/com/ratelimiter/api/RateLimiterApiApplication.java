package com.ratelimiter.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ratelimiter.api", "com.ratelimiter.redis"})
public class RateLimiterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateLimiterApiApplication.class, args);
    }
}