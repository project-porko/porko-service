package io.porko.domain.widget.event;

import io.porko.domain.widget.repo.WidgetRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateWidgetsEventListener {
    private final WidgetRepo widgetRepo;

    @Async
    @EventListener
    public void onApplicationReadyCreationWidgets(CreateInitialWidgetsEvent event) {
        log.info("onApplicationReadyCreationWidgets : {}", event);
        widgetRepo.saveAll(event.widgets().elements());
    }
}
