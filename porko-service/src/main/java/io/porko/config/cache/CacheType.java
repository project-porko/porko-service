package io.porko.config.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum CacheType {
    WIDGETS("widgets", 24, 10000);

    private final String name;
    private final long timeToLiveHours;
    private final int maxElementSize;
}
