package io.porko.global.config.cache;

import static io.porko.domain.widget.controller.WidgetControllerTestHelper.widgets;

import io.porko.global.config.base.ServiceBeanTestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class CacheConfigTestHelper extends ServiceBeanTestBase {
    protected final static String cacheName = CacheType.WIDGETS.getName();
    protected final static String testKey = "test::key";
    protected final static String testValue = "test-value";

    @BeforeEach
    void setUp() {
        cacheManager.getCache(CacheType.WIDGETS.getName()).putIfAbsent(CacheType.WIDGETS.getName(), widgets);
    }

    @AfterEach
    void tearDown() {
        cacheManager.getCache(CacheType.WIDGETS.getName()).invalidate();
    }
}
