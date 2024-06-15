package io.porko.domain.widget.controller;

import static io.porko.domain.widget.controller.WidgetController.WIDGET_BASE_URI;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Controller:Widget")
class WidgetControllerTest extends WidgetControllerTestHelper {
    @Test
    @DisplayName("[모든 위젯 조회][GET:200]")
    void getAllWidgets() throws Exception {
        // Given
        given(widgetService.loadAllWidgets()).willReturn(allWidgets);

        // When & Then
        get()
            .url(WIDGET_BASE_URI)
            .noAuthentication()
            .expect().ok();
    }
}
