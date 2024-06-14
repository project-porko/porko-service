package io.porko.widget.event;

import static org.mockito.Mockito.verify;

import io.porko.config.base.EventListenerTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@DisplayName("Event:CreateWidgetsEventListener")
class CreateInitialWidgetsEventListenerTest extends EventListenerTestBase {
    @Test
    @DisplayName("Spring Context 초기화 완료 시, 위젯 초기화 로직 이벤트 실행")
    void onApplicationReadyCreationWidgets() {
        // Then
        ArgumentCaptor<CreateInitialWidgetsEvent> eventCaptor = ArgumentCaptor.forClass(CreateInitialWidgetsEvent.class);
        verify(createWidgetsEventListener).onApplicationReadyCreationWidgets(eventCaptor.capture());
    }
}
