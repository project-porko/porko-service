package io.porko.widget.controller;

import static io.porko.widget.controller.WidgetController.WIDGET_BASE_URI;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Controller:Widget")
class WidgetControllerTest extends WidgetControllerTestHelper {
    // TODO: TC 기반 API docs 추출을 위해 Mocking된 응답에 Null로 설정된 ID(PK)필드 값 설정
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
