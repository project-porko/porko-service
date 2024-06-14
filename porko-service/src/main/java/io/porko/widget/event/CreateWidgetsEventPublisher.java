package io.porko.widget.event;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWidgetsEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        applicationEventPublisher.publishEvent(CreateInitialWidgetsEvent.initialWidgetsEvent());
    }
}
