package io.porko.domain.widget.service;


import io.porko.domain.widget.controller.model.WidgetsResponse;
import io.porko.domain.widget.repo.WidgetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WidgetService {
    private final WidgetRepo widgetRepo;

    @Cacheable(cacheNames = "widgets", key = "#root.methodName")
    public WidgetsResponse loadAllWidgets() {
        return WidgetsResponse.from(widgetRepo.findAll());
    }
}
