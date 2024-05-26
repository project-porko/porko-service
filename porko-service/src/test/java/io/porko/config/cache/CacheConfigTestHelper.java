package io.porko.config.cache;

import io.porko.config.base.TestBase;
import org.springframework.context.annotation.Import;

@Import(CacheConfig.class)
class CacheConfigTestHelper extends TestBase {
    protected final static String cacheName = CacheType.WIDGETS.getName();
    protected final static String testKey = "test::key";
    protected final static String testValue = "test-value";
}
