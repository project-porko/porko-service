package io.porko.widget.event;

import io.porko.widget.repo.WidgetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWidgetsEventListener {
    private final WidgetRepo widgetRepo;

    @Async
    @EventListener
    public void onApplicationReadyCreationWidgets(CreateInitialWidgetsEvent event) {
        widgetRepo.saveAll(event.widgets().elements());
    }
}
