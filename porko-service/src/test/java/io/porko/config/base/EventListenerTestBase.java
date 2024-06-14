package io.porko.config.base;

import io.porko.account.event.InitializeDataEventListener;
import io.porko.config.base.event.EventTestBase;
import io.porko.widget.event.CreateInitialMemberWidgetsEventListener;
import io.porko.widget.event.CreateWidgetsEventListener;
import org.springframework.boot.test.mock.mockito.MockBean;

public class EventListenerTestBase extends EventTestBase {
    @MockBean
    protected InitializeDataEventListener initializeDataEventListener;

    @MockBean
    protected CreateWidgetsEventListener createWidgetsEventListener;

    @MockBean
    protected CreateInitialMemberWidgetsEventListener createInitialMemberWidgetsEventListener;
}
