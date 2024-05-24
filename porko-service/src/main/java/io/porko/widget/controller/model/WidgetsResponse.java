package io.porko.widget.controller.model;

import io.porko.widget.domain.Widget;
import java.util.List;

public record WidgetsResponse(
    List<WidgetDto> elements
) {
    public static WidgetsResponse from(List<Widget> widgets) {
        return new WidgetsResponse(
            widgets.stream()
                .map(WidgetDto::from)
                .toList()
        );
    }
}
