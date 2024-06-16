package io.porko.domain.account.event;

import static org.mockito.Mockito.verify;

import io.porko.global.config.base.EventListenerTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@DisplayName("Event:InitializeTransactionDataEventListener")
class InitializeTransactionDataEventListenerTest extends EventListenerTestBase {
    @Test
    @DisplayName("Spring Context 초기화 완료 시, 데이터 초기화 로직 이벤트 실행")
    void onCompleteSpringContextLoadInitializeData() throws Exception {
        // Then
        ArgumentCaptor<InitializeTransactionDataEvent> eventCaptor = ArgumentCaptor.forClass(InitializeTransactionDataEvent.class);
        verify(initializeTransactionDataEventListener).onInitializeTransactionData(eventCaptor.capture());
    }
}
