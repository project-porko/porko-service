package io.porko.widget.controller.model;

import io.porko.widget.domain.Widget;
import java.util.List;
import java.util.stream.Collectors;

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
            .collect(Collectors.toList());
    }

    public int size(){
        return elements.size();
    }
}
