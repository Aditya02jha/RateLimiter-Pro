package com.ratelimiter.api.entity;

public enum StrategyType {
    FIXED_WINDOW,
    SLIDING_WINDOW,
    TOKEN_BUCKET,
    LEAKY_BUCKET
}
