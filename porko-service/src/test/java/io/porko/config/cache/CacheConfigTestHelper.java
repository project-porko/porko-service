package io.porko.config.cache;

import static io.porko.widget.controller.WidgetControllerTestHelper.widgets;

import io.porko.config.base.WebLayerTestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class CacheConfigTestHelper extends WebLayerTestBase {
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
