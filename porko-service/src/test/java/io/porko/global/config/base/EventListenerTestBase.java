package io.porko.global.config.base;

import io.porko.config.base.event.EventTestBase;
import io.porko.domain.account.event.InitializeTransactionDataEventListener;
import io.porko.domain.widget.event.CreateInitialMemberWidgetsEventListener;
import io.porko.domain.widget.event.CreateWidgetsEventListener;
import org.springframework.boot.test.mock.mockito.MockBean;

// TODO https://www.baeldung.com/spring-test-application-events
// TODO https://www.baeldung.com/spring-test-application-events
// TODO https://www.baeldung.com/spring-testexecutionlistener
// TODO https://miiiinju.tistory.com/21
public class EventListenerTestBase extends EventTestBase {
    @MockBean
    protected InitializeTransactionDataEventListener initializeTransactionDataEventListener;

    @MockBean
    protected CreateWidgetsEventListener createWidgetsEventListener;

    @MockBean
    protected CreateInitialMemberWidgetsEventListener createInitialMemberWidgetsEventListener;
}
