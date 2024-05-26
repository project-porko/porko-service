package io.porko.widget.service;

import static io.porko.config.utils.TestUtils.repeat;
import static io.porko.widget.controller.WidgetControllerTestHelper.widgets;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.porko.config.base.TestBase;
import io.porko.config.cache.CacheConfig;
import io.porko.config.cache.CacheType;
import io.porko.widget.controller.model.WidgetsResponse;
import io.porko.widget.repo.WidgetRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;

@DisplayName("Service:Widget")
@Import(CacheConfig.class)
@WebMvcTest(controllers = {CacheManager.class, WidgetService.class})
class WidgetServiceTest extends TestBase {
    @MockBean
    private WidgetRepo widgetRepo;

    @SpyBean
    private CacheManager cacheManager;

    private final WidgetService widgetService;

    public WidgetServiceTest(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @Test
    @DisplayName("캐시를 통한 모든 위젯 조회")
    void loadAllWidgets() {
        // Given
        final int repeatCount = 10;
        given(widgetRepo.findAll()).willReturn(widgets);

        // When
        repeat(repeatCount, () -> {
            WidgetsResponse widgetsResponse = widgetService.loadAllWidgets();
            System.out.println(widgetsResponse);
        });

        // Then
        verify(cacheManager, times(repeatCount)).getCache(CacheType.WIDGETS.getName());
        verify(widgetRepo, times(1)).findAll();
    }
}
