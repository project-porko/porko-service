package io.porko.widget.service;


import io.porko.widget.controller.model.WidgetsResponse;
import io.porko.widget.repo.WidgetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WidgetService {
    private final WidgetRepo widgetRepo;

    public WidgetsResponse loadAllWidgets() {
        return WidgetsResponse.from(widgetRepo.findAll());
    }
}
