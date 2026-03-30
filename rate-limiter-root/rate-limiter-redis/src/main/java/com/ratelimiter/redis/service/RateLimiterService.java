package com.ratelimiter.redis.service;

import response.RateLimitResponse;

public interface RateLimiterService {
    void incrementCounterExpireIfNew(String key, long expireTimeInSeconds);
    long getCounter(String key);
    RateLimitResponse incrementAndGet(String key, int windowSeconds, int limit);
    RateLimitResponse evaluateSlidingWindow(String key, int windowSeconds, int limit);
}
