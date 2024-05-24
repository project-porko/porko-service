package io.porko.widget.service;

import static io.porko.widget.service.WidgetTestHelper.widgets;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import io.porko.config.base.business.ServiceTestBase;
import io.porko.widget.controller.model.WidgetsResponse;
import io.porko.widget.repo.WidgetRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("Service:Widget")
class WidgetServiceTest extends ServiceTestBase {
    @Mock
    private WidgetRepo widgetRepo;

    @InjectMocks
    private WidgetService widgetService;

    @Test
    @DisplayName("모든 위젯 조회")
    void loadAllWidgets() {
        // Given
        given(widgetRepo.findAll()).willReturn(widgets);

        // When
        WidgetsResponse widgetsResponse = widgetService.loadAllWidgets();

        // Then
        verify(widgetRepo).findAll();
    }
}
