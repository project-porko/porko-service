package io.porko.domain.widget.controller.model;

import io.porko.domain.widget.domain.Widget;
import java.util.List;

// rename to widgetsDto
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

    public List<Widget> toWidgets() {
        return elements().stream()
            .map(it -> Widget.of(it.id(), it.code()))
            .toList();
    }

    public int size() {
        return elements.size();
    }
}
