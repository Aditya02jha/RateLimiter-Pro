package com.ratelimiter.api.repo;

import com.ratelimiter.api.entity.RateLimitRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateLimitRuleRepo extends JpaRepository<RateLimitRule, Long> {}
