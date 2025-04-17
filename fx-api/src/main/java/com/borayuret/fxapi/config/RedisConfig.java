package com.borayuret.fxapi.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * Configures the Redis CacheManager with a default TTL (Time To Live).
     * All @Cacheable entries will expire after the defined duration.
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)) // ⏱️ TTL = 10 minutes
                .disableCachingNullValues(); // Prevent caching null responses

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .enableStatistics()
                .build();
    }
}

