package io.porko.config.base.event;

import io.porko.config.base.TestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@SpringBootTest
@RecordApplicationEvents
public abstract class EventTestBase extends TestBase {
    @Autowired
    protected ApplicationEvents events;
}
