package io.porko.global.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager(List<CaffeineCache> caffeineCaches) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caffeineCaches);
        return cacheManager;
    }

    @Bean
    public List<CaffeineCache> caffeineCaches() {
        return Arrays.stream(CacheType.values())
            .map(this::createCaffeineCache)
            .toList();
    }

    private CaffeineCache createCaffeineCache(CacheType cacheType) {
        return new CaffeineCache(cacheType.getName(), cacheBuilder(cacheType));
    }

    private Cache<Object, Object> cacheBuilder(CacheType cacheType) {
        return Caffeine.newBuilder()
            .expireAfterWrite(cacheType.getMaxElementSize(), TimeUnit.HOURS)
            .maximumSize(cacheType.getMaxElementSize())
            .evictionListener((Object key, Object value, RemovalCause cause) ->
                log.warn("Key [{}] was evicted ({}): {}", key, cause, value)
            )
            .recordStats()
            .build();
    }
}
