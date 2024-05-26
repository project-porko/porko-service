package io.porko.config.cache;

import static io.porko.widget.controller.WidgetControllerTestHelper.widgets;

import io.porko.config.base.TestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;

@Import(CacheConfig.class)
class CacheConfigTestHelper extends TestBase {
    protected final static String cacheName = CacheType.WIDGETS.getName();
    protected final static String testKey = "test::key";
    protected final static String testValue = "test-value";

    @SpyBean
    protected CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager.getCache(CacheType.WIDGETS.getName()).putIfAbsent(CacheType.WIDGETS.getName(), widgets);
    }

    @AfterEach
    void tearDown() {
        cacheManager.getCache(CacheType.WIDGETS.getName()).invalidate();
    }
}
