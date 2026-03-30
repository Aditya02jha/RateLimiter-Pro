package com.ratelimiter.api.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "rate_limit_rule")
public class RateLimitRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;
    private Enum<StrategyType> strategyType;
    private int limit;
    private int windowSizeInSeconds;
    private Enum<Tier> tier;
    private DateTimeFormat createdAt;
    private DateTimeFormat updatedAt;
}
