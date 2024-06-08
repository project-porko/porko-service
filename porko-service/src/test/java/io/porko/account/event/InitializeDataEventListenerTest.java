package io.porko.account.event;

import static org.mockito.Mockito.verify;

import io.porko.config.base.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@SpringBootTest
@RecordApplicationEvents
@DisplayName("Event:InitializeDataEvent")
class InitializeDataEventListenerTest extends TestBase {
    @Autowired
    private ApplicationEvents events;

    @SpyBean
    private InitializeDataEventListener initializeDataEventListener;

    // TODO ApplicationReadyEvent -> 데이터 초기화에 대한 Event를 발행하고 발행 여부 및 EventListener에 대한 호출 여부 검증
    @Test
    @DisplayName("Spring Context 초기화 완료 시, 데이터 초기화 로직 이벤트 실행")
    void onCompleteSpringContextLoadInitializeData() throws Exception {
        // Then
        verify(initializeDataEventListener).init();
    }
}
