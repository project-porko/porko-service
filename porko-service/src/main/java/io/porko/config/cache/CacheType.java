package io.porko.config.cache;

import static io.porko.config.cache.CacheType.DefaultCacheProperties.DEFAULT_MAX_ELEMENTS_SIZE;
import static io.porko.config.cache.CacheType.DefaultCacheProperties.TIME_TO_LIVE_HOURS;

import lombok.Getter;

@Getter
public enum CacheType {
    WIDGETS("widgets");

    private final String name;
    private final long timeToLiveHours;
    private final int maxElementSize;

    CacheType(String name) {
        this.name = name;
        this.timeToLiveHours = TIME_TO_LIVE_HOURS;
        this.maxElementSize = DEFAULT_MAX_ELEMENTS_SIZE;
    }

    static class DefaultCacheProperties {
        static final int TIME_TO_LIVE_HOURS = 24;
        static final int DEFAULT_MAX_ELEMENTS_SIZE = 10000;
    }
}
